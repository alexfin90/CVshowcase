package com.alexfin90.logging.redaction

import com.alexfin90.logging.LogEntry

object NoOpRedaction : Redaction {
    override fun redact(entry: LogEntry): LogEntry = entry
}
