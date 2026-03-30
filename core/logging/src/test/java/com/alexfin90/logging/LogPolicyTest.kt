package com.alexfin90.logging

import android.util.Log
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class LogPolicyTest {

    private fun entry(priority: Int, tag: String? = "TestTag") = LogEntry(
        priority = priority,
        tag = tag,
        message = "test message",
        throwable = null,
    )

    @Test
    fun `disabled policy rejects all entries`() {
        val policy = LogPolicy(enabled = false)
        assertFalse(policy.accepts(entry(Log.ERROR)))
    }

    @Test
    fun `accepts entry at minPriority`() {
        val policy = LogPolicy(minPriority = Log.WARN)
        assertTrue(policy.accepts(entry(Log.WARN)))
        assertTrue(policy.accepts(entry(Log.ERROR)))
    }

    @Test
    fun `rejects entry below minPriority`() {
        val policy = LogPolicy(minPriority = Log.WARN)
        assertFalse(policy.accepts(entry(Log.DEBUG)))
        assertFalse(policy.accepts(entry(Log.VERBOSE)))
    }

    @Test
    fun `remoteDebugSession overrides minPriority to VERBOSE`() {
        val policy = LogPolicy(minPriority = Log.ERROR, remoteDebugSession = true)
        assertTrue(policy.accepts(entry(Log.VERBOSE)))
        assertTrue(policy.accepts(entry(Log.DEBUG)))
    }

    @Test
    fun `tagsBlocklist rejects matching tag`() {
        val policy = LogPolicy(minPriority = Log.VERBOSE, tagsBlocklist = setOf("NoisyTag"))
        assertFalse(policy.accepts(entry(Log.DEBUG, "NoisyTag")))
        assertTrue(policy.accepts(entry(Log.DEBUG, "OtherTag")))
    }

    @Test
    fun `tagsBlocklist with null tag passes through`() {
        val policy = LogPolicy(minPriority = Log.VERBOSE, tagsBlocklist = setOf("NoisyTag"))
        assertTrue(policy.accepts(entry(Log.DEBUG, null)))
    }

    @Test
    fun `tagsAllowlist accepts only matching tags`() {
        val policy = LogPolicy(minPriority = Log.VERBOSE, tagsAllowlist = setOf("AllowedTag"))
        assertTrue(policy.accepts(entry(Log.DEBUG, "AllowedTag")))
        assertFalse(policy.accepts(entry(Log.DEBUG, "OtherTag")))
    }

    @Test
    fun `tagsAllowlist rejects null tag`() {
        val policy = LogPolicy(minPriority = Log.VERBOSE, tagsAllowlist = setOf("AllowedTag"))
        assertFalse(policy.accepts(entry(Log.DEBUG, null)))
    }

    @Test
    fun `sampleRate 0 rejects all entries`() {
        val policy = LogPolicy(minPriority = Log.VERBOSE, sampleRate = 0.0f)
        repeat(20) {
            assertFalse(policy.accepts(entry(Log.DEBUG)))
        }
    }

    @Test
    fun `sampleRate 1 accepts all entries`() {
        val policy = LogPolicy(minPriority = Log.VERBOSE, sampleRate = 1.0f)
        repeat(20) {
            assertTrue(policy.accepts(entry(Log.DEBUG)))
        }
    }
}
