package com.alexfin90.logging.pipeline

import android.util.Log
import com.alexfin90.logging.CrashReporterSink
import com.alexfin90.logging.LogEntry
import com.alexfin90.logging.LogPolicy
import com.alexfin90.logging.LogSink
import com.alexfin90.logging.MutableLogPolicy
import com.alexfin90.logging.NonFatalReport
import com.alexfin90.logging.redaction.Redaction
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class LogPipelineTest {

    private fun makeEntry(priority: Int = Log.DEBUG, tag: String = "Tag", message: String = "msg") =
        LogEntry(priority, tag, message, null)

    private fun fakeSink(
        name: String = "fake",
        policyOverride: LogPolicy? = null,
        onLog: (LogEntry) -> Unit = {},
    ) = object : LogSink {
        override val name = name
        override val policyOverride = policyOverride
        val logged = mutableListOf<LogEntry>()
        override fun log(entry: LogEntry) {
            logged += entry
            onLog(entry)
        }
    }

    private fun fakeCrashSink(
        policyOverride: LogPolicy? = null,
    ) = object : CrashReporterSink {
        override val name = "crash"
        override val policyOverride = policyOverride
        val logged = mutableListOf<LogEntry>()
        val reported = mutableListOf<NonFatalReport>()
        override fun log(entry: LogEntry) { logged += entry }
        override fun reportNonFatal(report: NonFatalReport) { reported += report }
    }

    @Test
    fun `dispatch sends entry to accepting sink`() {
        val sink = fakeSink()
        val policy = MutableLogPolicy(LogPolicy(minPriority = Log.VERBOSE))
        val pipeline = LogPipeline(listOf(sink), policy)

        pipeline.dispatch(makeEntry(Log.DEBUG))

        assertEquals(1, sink.logged.size)
    }

    @Test
    fun `dispatch skips entry not accepted by global policy`() {
        val sink = fakeSink()
        val policy = MutableLogPolicy(LogPolicy(minPriority = Log.ERROR))
        val pipeline = LogPipeline(listOf(sink), policy)

        pipeline.dispatch(makeEntry(Log.DEBUG))

        assertTrue(sink.logged.isEmpty())
    }

    @Test
    fun `per-sink policy override takes precedence over global`() {
        val verboseSink = fakeSink(policyOverride = LogPolicy(minPriority = Log.VERBOSE))
        val warnSink = fakeSink(policyOverride = LogPolicy(minPriority = Log.WARN))
        val globalPolicy = MutableLogPolicy(LogPolicy(minPriority = Log.ERROR))
        val pipeline = LogPipeline(listOf(verboseSink, warnSink), globalPolicy)

        pipeline.dispatch(makeEntry(Log.DEBUG))

        assertEquals(1, verboseSink.logged.size)
        assertTrue(warnSink.logged.isEmpty())
    }

    @Test
    fun `redaction is applied before dispatch`() {
        val sink = fakeSink()
        val redaction = Redaction { entry -> entry.copy(message = entry.message.replace("secret", "[REDACTED]")) }
        val policy = MutableLogPolicy(LogPolicy(minPriority = Log.VERBOSE))
        val pipeline = LogPipeline(listOf(sink), policy, redaction)

        pipeline.dispatch(makeEntry(message = "my secret value"))

        assertEquals("[REDACTED]", sink.logged.first().message.trim().let {
            if (it.contains("[REDACTED]")) "[REDACTED]" else it
        })
        assertTrue(sink.logged.first().message.contains("[REDACTED]"))
        assertTrue(!sink.logged.first().message.contains("secret"))
    }

    @Test
    fun `dispatchNonFatal routes to CrashReporterSink only`() {
        val normalSink = fakeSink()
        val crashSink = fakeCrashSink()
        val policy = MutableLogPolicy(LogPolicy(minPriority = Log.VERBOSE))
        val pipeline = LogPipeline(listOf(normalSink, crashSink), policy)

        val report = NonFatalReport(throwable = RuntimeException("boom"))
        pipeline.dispatchNonFatal(report)

        assertTrue(normalSink.logged.isEmpty())
        assertEquals(1, crashSink.reported.size)
        assertEquals(report, crashSink.reported.first())
    }

    @Test
    fun `updatePolicy changes the effective policy`() {
        val sink = fakeSink()
        val policy = MutableLogPolicy(LogPolicy(minPriority = Log.WARN))
        val pipeline = LogPipeline(listOf(sink), policy)

        pipeline.dispatch(makeEntry(Log.DEBUG))
        assertTrue(sink.logged.isEmpty())

        pipeline.updatePolicy { copy(minPriority = Log.VERBOSE) }
        pipeline.dispatch(makeEntry(Log.DEBUG))
        assertEquals(1, sink.logged.size)
    }

    @Test
    fun `resetPolicy restores original policy`() {
        val sink = fakeSink()
        val policy = MutableLogPolicy(LogPolicy(minPriority = Log.WARN))
        val pipeline = LogPipeline(listOf(sink), policy)

        pipeline.updatePolicy { copy(minPriority = Log.VERBOSE) }
        pipeline.resetPolicy()

        pipeline.dispatch(makeEntry(Log.DEBUG))
        assertTrue(sink.logged.isEmpty())
    }
}
