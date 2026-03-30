package com.alexfin90.cvshowcase.logging

import android.util.Log
import com.alexfin90.logging.CrashReporterSink
import com.alexfin90.logging.LogEntry
import com.alexfin90.logging.LogPolicy
import com.alexfin90.logging.NonFatalReport
import com.google.firebase.crashlytics.FirebaseCrashlytics

class CrashlyticsSink(
    override val policyOverride: LogPolicy? = null,
) : CrashReporterSink {

    override val name: String = "crashlytics"

    override fun log(entry: LogEntry) {
        val throwable = entry.throwable ?: return
        val code = when (throwable) {
            is com.alexfin90.logging.exception.NonFatalException -> throwable.code
            is com.alexfin90.domain.exception.NonFatalException -> throwable.code
            else -> return
        }
        recordToCrashlytics(entry.priority, entry.tag ?: "Logwood", entry.message, throwable, code)
    }

    override fun reportNonFatal(report: NonFatalReport) {
        try {
            val crashlytics = FirebaseCrashlytics.getInstance()
            report.fingerprint?.let { crashlytics.setCustomKey(KEY_FINGERPRINT, it) }
            report.attributes.forEach { (k, v) -> crashlytics.setCustomKey(k, v) }
            crashlytics.recordException(report.throwable)
        } catch (_: IllegalStateException) {
            // Crashlytics not initialized (e.g., running in tests)
        }
    }

    private fun recordToCrashlytics(
        priority: Int,
        tag: String,
        message: String,
        throwable: Throwable,
        code: String,
    ) {
        try {
            val crashlytics = FirebaseCrashlytics.getInstance()
            crashlytics.setCustomKey(KEY_PRIORITY, priorityLabel(priority))
            crashlytics.setCustomKey(KEY_TAG, tag)
            crashlytics.setCustomKey(KEY_MESSAGE, message)
            crashlytics.setCustomKey(KEY_ERROR_CODE, code)
            crashlytics.log("$tag: $message")
            crashlytics.recordException(throwable)
        } catch (_: IllegalStateException) {
            // Crashlytics not initialized (e.g., running in tests)
        }
    }

    private fun priorityLabel(priority: Int): String = when (priority) {
        Log.ERROR -> "ERROR"
        Log.WARN -> "WARN"
        Log.ASSERT -> "ASSERT"
        else -> "UNKNOWN"
    }

    companion object {
        private const val KEY_PRIORITY = "priority"
        private const val KEY_TAG = "tag"
        private const val KEY_MESSAGE = "message"
        private const val KEY_ERROR_CODE = "code"
        private const val KEY_FINGERPRINT = "fingerprint"
    }
}
