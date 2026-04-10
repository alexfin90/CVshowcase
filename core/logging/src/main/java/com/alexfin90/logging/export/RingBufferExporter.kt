package com.alexfin90.logging.export

import android.util.Log
import com.alexfin90.logging.LogEntry
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object RingBufferExporter {

    private val DATE_FORMAT = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.US)

    fun toPlainText(entries: List<LogEntry>): String = buildString {
        for (entry in entries) {
            val priorityLabel = priorityLabel(entry.priority)
            val timestamp = DATE_FORMAT.format(Date(entry.timestamp))
            appendLine("$timestamp $priorityLabel/${entry.tag ?: "?"}: ${entry.message}")
            entry.throwable?.let { appendLine(it.stackTraceToString()) }
        }
    }

    fun toJsonLines(entries: List<LogEntry>): String = buildString {
        for (entry in entries) {
            val throwableJson = entry.throwable?.let { ", \"throwable\": \"${escapeJson(it.toString())}\"" } ?: ""
            appendLine(
                "{\"ts\": ${entry.timestamp}, \"pri\": ${entry.priority}, " +
                    "\"tag\": \"${escapeJson(entry.tag ?: "")}\", " +
                    "\"msg\": \"${escapeJson(entry.message)}\", " +
                    "\"thread\": \"${escapeJson(entry.threadName)}\"$throwableJson}"
            )
        }
    }

    private fun priorityLabel(priority: Int): String = when (priority) {
        Log.VERBOSE -> "V"
        Log.DEBUG -> "D"
        Log.INFO -> "I"
        Log.WARN -> "W"
        Log.ERROR -> "E"
        Log.ASSERT -> "A"
        else -> "?"
    }

    private fun escapeJson(s: String): String = s
        .replace("\\", "\\\\")
        .replace("\"", "\\\"")
        .replace("\n", "\\n")
        .replace("\r", "\\r")
        .replace("\t", "\\t")
}
