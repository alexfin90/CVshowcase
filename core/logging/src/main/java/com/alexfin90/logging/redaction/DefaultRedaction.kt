package com.alexfin90.logging.redaction

import com.alexfin90.logging.LogEntry

class DefaultRedaction : Redaction {

    override fun redact(entry: LogEntry): LogEntry {
        val redacted = entry.message
            .replace(EMAIL_REGEX, "[EMAIL]")
            .replace(PHONE_REGEX, "[PHONE]")
            .replace(IP_REGEX, "[IP]")
            .replace(TOKEN_REGEX, "[TOKEN]")
            .replace(GPS_REGEX, "[GPS]")
        return entry.copy(message = redacted)
    }

    companion object {
        private val EMAIL_REGEX = Regex(
            "[a-zA-Z0-9._%+\\-]+@[a-zA-Z0-9.\\-]+\\.[a-zA-Z]{2,}"
        )
        private val PHONE_REGEX = Regex(
            "(?<![\\w.])(?:\\+?[0-9][\\s\\-.]?){7,14}[0-9](?![\\w.])"
        )
        private val IP_REGEX = Regex(
            "\\b(?:\\d{1,3}\\.){3}\\d{1,3}\\b"
        )
        private val TOKEN_REGEX = Regex(
            "(?i)(?:bearer\\s+|token[=:\\s]+)[a-zA-Z0-9\\-._~+/]+=*"
        )
        private val GPS_REGEX = Regex(
            "(-?\\d{1,3}\\.\\d{4,})[,\\s]+(-?\\d{1,3}\\.\\d{4,})"
        )
    }
}
