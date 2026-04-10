package com.alexfin90.logging

import android.util.Log
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class MutableLogPolicyTest {

    @Test
    fun `get returns initial policy`() {
        val initial = LogPolicy(minPriority = Log.DEBUG)
        val mutable = MutableLogPolicy(initial)
        assertEquals(initial, mutable.get())
    }

    @Test
    fun `update changes current policy`() {
        val mutable = MutableLogPolicy(LogPolicy(minPriority = Log.WARN))
        mutable.update { copy(minPriority = Log.VERBOSE) }
        assertEquals(Log.VERBOSE, mutable.get().minPriority)
    }

    @Test
    fun `reset restores original policy`() {
        val original = LogPolicy(minPriority = Log.WARN)
        val mutable = MutableLogPolicy(original)
        mutable.update { copy(minPriority = Log.VERBOSE, remoteDebugSession = true) }
        mutable.reset()
        assertEquals(original, mutable.get())
    }

    @Test
    fun `update is thread-safe`() {
        val mutable = MutableLogPolicy(LogPolicy(minPriority = Log.WARN, enabled = true))
        val executor = Executors.newFixedThreadPool(8)
        val latch = CountDownLatch(100)
        repeat(100) {
            executor.submit {
                mutable.update { copy(enabled = !enabled) }
                latch.countDown()
            }
        }
        assertTrue(latch.await(5, TimeUnit.SECONDS))
        executor.shutdown()
        // After 100 toggles (even number), enabled should be back to original true
        assertTrue(mutable.get().enabled)
    }

    @Test
    fun `reset after multiple updates restores original`() {
        val original = LogPolicy(minPriority = Log.ERROR, enabled = false, sampleRate = 0.5f)
        val mutable = MutableLogPolicy(original)
        mutable.update { copy(minPriority = Log.VERBOSE) }
        mutable.update { copy(enabled = true) }
        mutable.update { copy(sampleRate = 1.0f) }
        mutable.reset()
        assertEquals(original, mutable.get())
    }
}
