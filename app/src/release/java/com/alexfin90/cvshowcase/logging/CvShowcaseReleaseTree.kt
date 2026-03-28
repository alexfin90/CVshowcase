package com.alexfin90.cvshowcase.logging

import android.util.Log
import com.alexfin90.domain.exception.NonFatalException
import com.google.firebase.crashlytics.FirebaseCrashlytics
import timber.log.Timber

object CvShowcaseReleaseTree : Timber.Tree() {

    private const val TAG = "CvShowcase"
    private const val CRASHLYTICS_KEY_PRIORITY = "priority"
    private const val CRASHLYTICS_KEY_TAG = "tag"
    private const val CRASHLYTICS_KEY_MESSAGE = "message"
    private const val CRASHLYTICS_KEY_ERROR_CODE = "code"

    override fun isLoggable(tag: String?, priority: Int): Boolean {
        return priority >= Log.WARN
    }

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        val logTag = tag ?: TAG

        // Always output to Logcat for WARN/ERROR/ASSERT
        Log.println(priority, logTag, message)

        // Send ONLY NonFatalException to Crashlytics
        if (t is NonFatalException) {
            recordToCrashlytics(priority, logTag, message, t)
        }
    }

    private fun recordToCrashlytics(
        priority: Int,
        tag: String,
        message: String,
        t: NonFatalException,
    ) {
        try {
            val crashlytics = FirebaseCrashlytics.getInstance()
            crashlytics.setCustomKey(CRASHLYTICS_KEY_PRIORITY, priorityLabel(priority))
            crashlytics.setCustomKey(CRASHLYTICS_KEY_TAG, tag)
            crashlytics.setCustomKey(CRASHLYTICS_KEY_MESSAGE, message)
            crashlytics.setCustomKey(CRASHLYTICS_KEY_ERROR_CODE, t.code)
            crashlytics.log("$tag: $message")
            crashlytics.recordException(t)
        } catch (_: IllegalStateException) {
            // Crashlytics not initialized (e.g., mock flavor)
        }
    }

    private fun priorityLabel(priority: Int): String = when (priority) {
        Log.ERROR -> "ERROR"
        Log.WARN -> "WARN"
        Log.ASSERT -> "ASSERT"
        else -> "UNKNOWN"
    }
}
