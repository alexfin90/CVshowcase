package com.alexfin90.logging

data class LogEntry(
    val priority: Int,
    val tag: String?,
    val message: String,
    val throwable: Throwable?,
    val timestamp: Long = System.currentTimeMillis(),
    val threadName: String = Thread.currentThread().name,
)
