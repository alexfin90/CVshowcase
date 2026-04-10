package com.alexfin90.logging

import android.util.Log
import com.alexfin90.logging.redaction.DefaultRedaction
import com.alexfin90.logging.sink.InMemoryRingBufferSink
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import timber.log.Timber

class LogwoodInstallTest {

    private lateinit var captureSink: CaptureSink

    private class CaptureSink(override val policyOverride: LogPolicy? = null) : LogSink {
        override val name = "capture"
        val entries = mutableListOf<LogEntry>()
        override fun log(entry: LogEntry) { entries += entry }
    }

    @Before
    fun setUp() {
        Timber.uprootAll()
        captureSink = CaptureSink()
    }

    @After
    fun tearDown() {
        Timber.uprootAll()
    }

    @Test
    fun `install plants LogwoodTimberTree`() {
        Logwood.install {
            policy { minPriority = Log.VERBOSE }
            sinks { add(captureSink) }
        }
        assertTrue(Timber.treeCount > 0)
    }

    @Test
    fun `install dispatches Timber logs through sinks`() {
        Logwood.install {
            policy { minPriority = Log.VERBOSE }
            sinks { add(captureSink) }
        }
        Timber.tag("T").d("hello install")
        assertEquals(1, captureSink.entries.size)
        assertEquals("hello install", captureSink.entries.first().message)
    }

    @Test
    fun `policy minPriority is respected`() {
        Logwood.install {
            policy { minPriority = Log.WARN }
            sinks { add(captureSink) }
        }
        Timber.tag("T").d("should not appear")
        Timber.tag("T").w("should appear")
        assertEquals(1, captureSink.entries.size)
        assertEquals("should appear", captureSink.entries.first().message)
    }

    @Test
    fun `updatePolicy changes filtering`() {
        Logwood.install {
            policy { minPriority = Log.WARN }
            sinks { add(captureSink) }
        }
        Timber.tag("T").d("before update")
        assertTrue(captureSink.entries.isEmpty())

        Logwood.updatePolicy { copy(minPriority = Log.VERBOSE) }
        Timber.tag("T").d("after update")
        assertEquals(1, captureSink.entries.size)
    }

    @Test
    fun `resetPolicy restores original filtering`() {
        Logwood.install {
            policy { minPriority = Log.WARN }
            sinks { add(captureSink) }
        }
        Logwood.updatePolicy { copy(minPriority = Log.VERBOSE) }
        Logwood.resetPolicy()
        Timber.tag("T").d("should not appear after reset")
        assertTrue(captureSink.entries.isEmpty())
    }

    @Test
    fun `exportRingBuffer returns plain text logs`() {
        val ringBuffer = InMemoryRingBufferSink(maxEntries = 10)
        Logwood.install {
            policy { minPriority = Log.VERBOSE }
            sinks { add(ringBuffer) }
        }
        Timber.tag("T").d("ring entry 1")
        Timber.tag("T").d("ring entry 2")
        val export = Logwood.exportRingBuffer()
        assertTrue(export.contains("ring entry 1"))
        assertTrue(export.contains("ring entry 2"))
    }

    @Test
    fun `tagsBlocklist filters out noisy tags`() {
        Logwood.install {
            policy {
                minPriority = Log.VERBOSE
                tagsBlocklist = setOf("NoisyTag")
            }
            sinks { add(captureSink) }
        }
        Timber.tag("NoisyTag").d("noisy")
        Timber.tag("OtherTag").d("useful")
        assertEquals(1, captureSink.entries.size)
        assertEquals("useful", captureSink.entries.first().message)
    }

    @Test
    fun `redaction is applied before sinks receive entry`() {
        Logwood.install {
            policy { minPriority = Log.VERBOSE }
            sinks { add(captureSink) }
            redaction(DefaultRedaction())
        }
        Timber.tag("T").d("Contact: user@example.com")
        assertEquals(1, captureSink.entries.size)
        assertFalse(captureSink.entries.first().message.contains("user@example.com"))
        assertTrue(captureSink.entries.first().message.contains("[EMAIL]"))
    }
}
