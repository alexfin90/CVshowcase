package com.alexfin90.logging.sink

import android.util.Log
import com.alexfin90.logging.LogEntry
import com.alexfin90.logging.LogPolicy
import com.alexfin90.logging.LogSink

class LogcatSink(
    override val name: String = "logcat",
    override val policyOverride: LogPolicy? = null,
) : LogSink {

    override fun log(entry: LogEntry) {
        val tag = entry.tag ?: DEFAULT_TAG
        val message = if (entry.throwable != null) {
            "${entry.message}\n${Log.getStackTraceString(entry.throwable)}"
        } else {
            entry.message
        }
        Log.println(entry.priority, tag, message)
    }

    companion object {
        private const val DEFAULT_TAG = "Logwood"
    }
}
