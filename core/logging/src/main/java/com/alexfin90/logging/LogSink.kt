package com.alexfin90.logging

interface LogSink {
    val name: String
    val policyOverride: LogPolicy? get() = null
    fun log(entry: LogEntry)
    fun flush() {}
}
