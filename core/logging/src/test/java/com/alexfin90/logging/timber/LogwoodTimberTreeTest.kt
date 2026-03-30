package com.alexfin90.logging.timber

import android.util.Log
import com.alexfin90.logging.LogEntry
import com.alexfin90.logging.LogPolicy
import com.alexfin90.logging.LogSink
import com.alexfin90.logging.MutableLogPolicy
import com.alexfin90.logging.NonFatalReport
import com.alexfin90.logging.exception.NonFatalException
import com.alexfin90.logging.pipeline.LogPipeline
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import timber.log.Timber

class LogwoodTimberTreeTest {

    private lateinit var dispatchedEntries: MutableList<LogEntry>
    private lateinit var reportedNonFatals: MutableList<NonFatalReport>
    private lateinit var pipeline: LogPipeline
    private lateinit var tree: LogwoodTimberTree

    @Before
    fun setUp() {
        Timber.uprootAll()
        dispatchedEntries = mutableListOf()
        reportedNonFatals = mutableListOf()
        val captureSink = object : LogSink {
            override val name = "capture"
            override fun log(entry: LogEntry) { dispatchedEntries += entry }
        }
        val crashSink = object : com.alexfin90.logging.CrashReporterSink {
            override val name = "crash"
            override fun log(entry: LogEntry) {}
            override fun reportNonFatal(report: NonFatalReport) { reportedNonFatals += report }
        }
        val policy = MutableLogPolicy(LogPolicy(minPriority = Log.VERBOSE))
        pipeline = LogPipeline(listOf(captureSink, crashSink), policy)
        tree = LogwoodTimberTree(pipeline)
        Timber.plant(tree)
    }

    @Test
    fun `Timber log dispatches correct LogEntry`() {
        Timber.tag("MyTag").d("Hello World")

        assertEquals(1, dispatchedEntries.size)
        val entry = dispatchedEntries.first()
        assertEquals("MyTag", entry.tag)
        assertEquals("Hello World", entry.message)
        assertEquals(Log.DEBUG, entry.priority)
    }

    @Test
    fun `Timber log with throwable captures throwable in entry`() {
        val ex = RuntimeException("oops")
        Timber.tag("ErrTag").e(ex, "error occurred")

        assertEquals(1, dispatchedEntries.size)
        assertEquals(ex, dispatchedEntries.first().throwable)
    }

    @Test
    fun `NonFatalException triggers dispatchNonFatal`() {
        val nfe = NonFatalException(code = "ERR_001", message = "non-fatal error")
        Timber.tag("Tag").e(nfe, nfe.message)

        assertEquals(1, reportedNonFatals.size)
        assertEquals(nfe, reportedNonFatals.first().throwable)
    }

    @Test
    fun `regular exception does not trigger dispatchNonFatal`() {
        val ex = RuntimeException("regular")
        Timber.tag("Tag").e(ex, "error")

        assertTrue(reportedNonFatals.isEmpty())
    }

    @After
    fun tearDown() {
        Timber.uproot(tree)
    }
}
