package com.alexfin90.logging.sink

import android.util.Log
import com.alexfin90.logging.LogEntry
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class InMemoryRingBufferSinkTest {

    private fun entry(msg: String) = LogEntry(Log.DEBUG, "Tag", msg, null)

    @Test
    fun `snapshot returns logged entries in order`() {
        val sink = InMemoryRingBufferSink(maxEntries = 10)
        sink.log(entry("first"))
        sink.log(entry("second"))
        sink.log(entry("third"))

        val snapshot = sink.snapshot()
        assertEquals(3, snapshot.size)
        assertEquals("first", snapshot[0].message)
        assertEquals("third", snapshot[2].message)
    }

    @Test
    fun `wraps around when maxEntries exceeded`() {
        val sink = InMemoryRingBufferSink(maxEntries = 3)
        sink.log(entry("1"))
        sink.log(entry("2"))
        sink.log(entry("3"))
        sink.log(entry("4"))

        val snapshot = sink.snapshot()
        assertEquals(3, snapshot.size)
        assertEquals("2", snapshot[0].message)
        assertEquals("4", snapshot[2].message)
    }

    @Test
    fun `clear removes all entries`() {
        val sink = InMemoryRingBufferSink(maxEntries = 10)
        sink.log(entry("a"))
        sink.log(entry("b"))
        sink.clear()
        assertTrue(sink.snapshot().isEmpty())
    }

    @Test
    fun `snapshot ordering is correct after many wraps`() {
        val sink = InMemoryRingBufferSink(maxEntries = 5)
        for (i in 1..12) sink.log(entry("msg$i"))
        val snapshot = sink.snapshot()
        assertEquals(5, snapshot.size)
        assertEquals("msg8", snapshot[0].message)
        assertEquals("msg12", snapshot[4].message)
    }

    @Test
    fun `concurrent writes are thread-safe`() {
        val sink = InMemoryRingBufferSink(maxEntries = 500)
        val executor = Executors.newFixedThreadPool(8)
        val latch = CountDownLatch(200)
        repeat(200) { i ->
            executor.submit {
                sink.log(entry("msg$i"))
                latch.countDown()
            }
        }
        assertTrue(latch.await(5, TimeUnit.SECONDS))
        executor.shutdown()
        assertEquals(200, sink.snapshot().size)
    }

    @Test
    fun `concurrent reads and writes do not deadlock`() {
        val sink = InMemoryRingBufferSink(maxEntries = 100)
        val executor = Executors.newFixedThreadPool(8)
        val latch = CountDownLatch(160)
        repeat(80) { i ->
            executor.submit { sink.log(entry("w$i")); latch.countDown() }
            executor.submit { sink.snapshot(); latch.countDown() }
        }
        assertTrue(latch.await(5, TimeUnit.SECONDS))
        executor.shutdown()
    }
}
