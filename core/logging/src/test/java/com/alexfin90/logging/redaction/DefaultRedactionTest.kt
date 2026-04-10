package com.alexfin90.logging.redaction

import android.util.Log
import com.alexfin90.logging.LogEntry
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class DefaultRedactionTest {

    private val redaction = DefaultRedaction()

    private fun redact(message: String): String {
        val entry = LogEntry(Log.DEBUG, "Tag", message, null)
        return redaction.redact(entry).message
    }

    @Test
    fun `redacts email address`() {
        val result = redact("User email: user@example.com is stored")
        assertTrue(result.contains("[EMAIL]"))
        assertFalse(result.contains("user@example.com"))
    }

    @Test
    fun `redacts IP address`() {
        val result = redact("Server at 192.168.1.1 responded")
        assertTrue(result.contains("[IP]"))
        assertFalse(result.contains("192.168.1.1"))
    }

    @Test
    fun `redacts bearer token`() {
        val result = redact("Authorization: Bearer abc123token456")
        assertTrue(result.contains("[TOKEN]"))
        assertFalse(result.contains("abc123token456"))
    }

    @Test
    fun `redacts GPS coordinates`() {
        val result = redact("Location: 51.5074, -0.1278")
        assertTrue(result.contains("[GPS]"))
        assertFalse(result.contains("51.5074"))
    }

    @Test
    fun `non-PII message is unchanged`() {
        val message = "User clicked button on screen"
        val result = redact(message)
        assertTrue(result.contains("User clicked button on screen"))
    }

    @Test
    fun `redacts multiple PII items in single message`() {
        val result = redact("Email: test@test.com from IP 10.0.0.1")
        assertTrue(result.contains("[EMAIL]"))
        assertTrue(result.contains("[IP]"))
        assertFalse(result.contains("test@test.com"))
        assertFalse(result.contains("10.0.0.1"))
    }

    @Test
    fun `does not modify entry tag or throwable`() {
        val throwable = RuntimeException("error")
        val entry = LogEntry(Log.ERROR, "TestTag", "user@example.com", throwable)
        val redacted = redaction.redact(entry)
        assertTrue(redacted.message.contains("[EMAIL]"))
        assertTrue(redacted.tag == "TestTag")
        assertTrue(redacted.throwable === throwable)
    }

    @Test
    fun `empty message is unchanged`() {
        val result = redact("")
        assertTrue(result.isEmpty())
    }
}
