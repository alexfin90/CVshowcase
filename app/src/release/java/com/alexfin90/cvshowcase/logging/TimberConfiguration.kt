package com.alexfin90.cvshowcase.logging

import android.util.Log
import com.alexfin90.logging.Logwood
import com.alexfin90.logging.redaction.DefaultRedaction

fun plantTimberTree() {
    val crashlyticsSink = CrashlyticsSink()
    Logwood.install {
        policy {
            minPriority = Log.WARN
            enabled = true
        }
        sinks {
            logcat()
            ringBuffer(maxEntries = 500)
            add(crashlyticsSink)
        }
        redaction(DefaultRedaction())
    }
}
