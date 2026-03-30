package com.alexfin90.logging

import android.util.Log
import java.util.concurrent.atomic.AtomicReference

data class LogPolicy(
    val minPriority: Int = Log.WARN,
    val enabled: Boolean = true,
    val sampleRate: Float = 1.0f,
    val tagsAllowlist: Set<String> = emptySet(),
    val tagsBlocklist: Set<String> = emptySet(),
    val remoteDebugSession: Boolean = false,
) {
    fun accepts(entry: LogEntry): Boolean {
        if (!enabled) return false
        val effectivePriority = if (remoteDebugSession) Log.VERBOSE else minPriority
        if (entry.priority < effectivePriority) return false
        if (tagsBlocklist.isNotEmpty() && entry.tag != null && entry.tag in tagsBlocklist) return false
        if (tagsAllowlist.isNotEmpty() && (entry.tag == null || entry.tag !in tagsAllowlist)) return false
        if (sampleRate < 1.0f && Math.random() >= sampleRate) return false
        return true
    }
}

class MutableLogPolicy(initial: LogPolicy) {
    private val original: LogPolicy = initial
    private val current: AtomicReference<LogPolicy> = AtomicReference(initial)

    fun get(): LogPolicy = current.get()

    fun update(transform: LogPolicy.() -> LogPolicy) {
        current.updateAndGet { it.transform() }
    }

    fun reset() {
        current.set(original)
    }
}
