package com.alexfin90.logging.pipeline

import com.alexfin90.logging.CrashReporterSink
import com.alexfin90.logging.LogEntry
import com.alexfin90.logging.LogSink
import com.alexfin90.logging.MutableLogPolicy
import com.alexfin90.logging.NonFatalReport
import com.alexfin90.logging.redaction.NoOpRedaction
import com.alexfin90.logging.redaction.Redaction

class LogPipeline(
    val sinks: List<LogSink>,
    val mutablePolicy: MutableLogPolicy,
    private val redaction: Redaction = NoOpRedaction,
) {
    fun dispatch(entry: LogEntry) {
        val redacted = redaction.redact(entry)
        val globalPolicy = mutablePolicy.get()
        for (sink in sinks) {
            val effectivePolicy = sink.policyOverride ?: globalPolicy
            if (effectivePolicy.accepts(redacted)) {
                sink.log(redacted)
            }
        }
    }

    fun dispatchNonFatal(report: NonFatalReport) {
        for (sink in sinks.filterIsInstance<CrashReporterSink>()) {
            sink.reportNonFatal(report)
        }
    }

    fun updatePolicy(transform: com.alexfin90.logging.LogPolicy.() -> com.alexfin90.logging.LogPolicy) {
        mutablePolicy.update(transform)
    }

    fun resetPolicy() {
        mutablePolicy.reset()
    }

    fun flush() {
        sinks.forEach { it.flush() }
    }
}
