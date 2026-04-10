package com.alexfin90.logging.sink

import com.alexfin90.logging.LogEntry
import com.alexfin90.logging.LogPolicy
import com.alexfin90.logging.LogSink
import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.read
import kotlin.concurrent.write

class InMemoryRingBufferSink(
    val maxEntries: Int = 200,
    override val name: String = "ring_buffer",
    override val policyOverride: LogPolicy? = null,
) : LogSink {

    private val lock = ReentrantReadWriteLock()
    private val buffer = ArrayDeque<LogEntry>(maxEntries)

    override fun log(entry: LogEntry) {
        lock.write {
            if (buffer.size >= maxEntries) buffer.removeFirst()
            buffer.addLast(entry)
        }
    }

    fun snapshot(): List<LogEntry> = lock.read { buffer.toList() }

    fun clear() {
        lock.write { buffer.clear() }
    }
}
