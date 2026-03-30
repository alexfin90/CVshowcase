package com.alexfin90.logging

import android.util.Log
import com.alexfin90.logging.export.RingBufferExporter
import com.alexfin90.logging.pipeline.LogPipeline
import com.alexfin90.logging.redaction.DefaultRedaction
import com.alexfin90.logging.redaction.NoOpRedaction
import com.alexfin90.logging.redaction.Redaction
import com.alexfin90.logging.sink.InMemoryRingBufferSink
import com.alexfin90.logging.sink.LogcatSink
import com.alexfin90.logging.timber.LogwoodTimberTree
import timber.log.Timber

object Logwood {

    @Volatile private var pipeline: LogPipeline? = null
    @Volatile private var ringBufferSink: InMemoryRingBufferSink? = null

    fun install(block: LogwoodBuilder.() -> Unit) {
        val builder = LogwoodBuilder().apply(block)
        val mutablePolicy = MutableLogPolicy(builder.policy)
        val builtSinks = builder.sinks.toList()
        val newPipeline = LogPipeline(builtSinks, mutablePolicy, builder.redaction)
        pipeline = newPipeline
        ringBufferSink = builtSinks.filterIsInstance<InMemoryRingBufferSink>().firstOrNull()
        Timber.plant(LogwoodTimberTree(newPipeline))
    }

    fun updatePolicy(transform: LogPolicy.() -> LogPolicy) {
        requirePipeline().updatePolicy(transform)
    }

    fun resetPolicy() {
        requirePipeline().resetPolicy()
    }

    fun reportNonFatal(
        throwable: Throwable,
        attributes: Map<String, String> = emptyMap(),
        fingerprint: String? = null,
    ) {
        requirePipeline().dispatchNonFatal(NonFatalReport(throwable, attributes, fingerprint))
    }

    fun exportRingBuffer(): String {
        val entries = ringBufferSink?.snapshot() ?: return ""
        return RingBufferExporter.toPlainText(entries)
    }

    fun exportRingBufferAsJsonLines(): String {
        val entries = ringBufferSink?.snapshot() ?: return ""
        return RingBufferExporter.toJsonLines(entries)
    }

    private fun requirePipeline(): LogPipeline =
        pipeline ?: error("Logwood not installed. Call Logwood.install {} first.")
}

class LogwoodBuilder {
    var policy: LogPolicy = LogPolicy()
    var redaction: Redaction = NoOpRedaction
    internal val sinks: MutableList<LogSink> = mutableListOf()

    fun policy(block: LogPolicyBuilder.() -> Unit) {
        policy = LogPolicyBuilder().apply(block).build()
    }

    fun sinks(block: SinkBuilder.() -> Unit) {
        val builder = SinkBuilder().apply(block)
        sinks.addAll(builder.sinks)
    }

    fun redaction(r: Redaction) {
        redaction = r
    }
}

class LogPolicyBuilder {
    var minPriority: Int = Log.WARN
    var enabled: Boolean = true
    var sampleRate: Float = 1.0f
    var tagsAllowlist: Set<String> = emptySet()
    var tagsBlocklist: Set<String> = emptySet()
    var remoteDebugSession: Boolean = false

    fun build(): LogPolicy = LogPolicy(
        minPriority = minPriority,
        enabled = enabled,
        sampleRate = sampleRate,
        tagsAllowlist = tagsAllowlist,
        tagsBlocklist = tagsBlocklist,
        remoteDebugSession = remoteDebugSession,
    )
}

class SinkBuilder {
    internal val sinks: MutableList<LogSink> = mutableListOf()

    fun logcat(policyOverride: LogPolicy? = null) {
        sinks += LogcatSink(policyOverride = policyOverride)
    }

    fun ringBuffer(
        maxEntries: Int = 200,
        policyOverride: LogPolicy? = null,
    ): InMemoryRingBufferSink {
        val sink = InMemoryRingBufferSink(maxEntries = maxEntries, policyOverride = policyOverride)
        sinks += sink
        return sink
    }

    fun add(sink: LogSink) {
        sinks += sink
    }

    fun addAll(vararg sink: LogSink) {
        sinks += sink
    }
}
