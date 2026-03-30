package com.alexfin90.logging.export

import android.util.Log
import com.alexfin90.logging.LogEntry
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class RingBufferExporterTest {

    private fun entry(
        priority: Int = Log.DEBUG,
        tag: String? = "TestTag",
        message: String = "test message",
        throwable: Throwable? = null,
    ) = LogEntry(priority, tag, message, throwable, timestamp = 1000000L, threadName = "main")

    @Test
    fun `toPlainText contains tag and message`() {
        val entries = listOf(entry(message = "Hello from log"))
        val result = RingBufferExporter.toPlainText(entries)
        assertTrue(result.contains("TestTag"))
        assertTrue(result.contains("Hello from log"))
    }

    @Test
    fun `toPlainText contains priority label`() {
        val result = RingBufferExporter.toPlainText(listOf(entry(priority = Log.WARN)))
        assertTrue(result.contains("W/"))
    }

    @Test
    fun `toPlainText with throwable includes stack trace`() {
        val ex = RuntimeException("boom")
        val result = RingBufferExporter.toPlainText(listOf(entry(throwable = ex)))
        assertTrue(result.contains("RuntimeException"))
    }

    @Test
    fun `toPlainText empty list returns empty string`() {
        val result = RingBufferExporter.toPlainText(emptyList())
        assertTrue(result.isEmpty())
    }

    @Test
    fun `toJsonLines produces valid JSON per line`() {
        val entries = listOf(entry(message = "json test"))
        val result = RingBufferExporter.toJsonLines(entries)
        val lines = result.trim().lines()
        assertTrue(lines.isNotEmpty())
        assertTrue(lines.first().startsWith("{"))
        assertTrue(lines.first().endsWith("}"))
        assertTrue(lines.first().contains("\"msg\""))
        assertTrue(lines.first().contains("json test"))
    }

    @Test
    fun `toJsonLines escapes special characters in message`() {
        val result = RingBufferExporter.toJsonLines(listOf(entry(message = "a \"quoted\" message")))
        assertFalse(result.contains("\"quoted\""))
        assertTrue(result.contains("\\\"quoted\\\""))
    }

    @Test
    fun `toJsonLines produces one line per entry`() {
        val entries = listOf(entry(message = "first"), entry(message = "second"))
        val lines = RingBufferExporter.toJsonLines(entries).trim().lines()
        assertTrue(lines.size == 2)
    }
}
