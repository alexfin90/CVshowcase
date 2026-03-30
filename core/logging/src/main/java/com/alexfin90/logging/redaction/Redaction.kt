package com.alexfin90.logging.redaction

import com.alexfin90.logging.LogEntry

fun interface Redaction {
    fun redact(entry: LogEntry): LogEntry
}
