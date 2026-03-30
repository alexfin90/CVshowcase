package com.alexfin90.cvshowcase.logging

import android.util.Log
import com.alexfin90.logging.Logwood
import com.alexfin90.logging.redaction.DefaultRedaction

fun plantTimberTree() {
    Logwood.install {
        policy {
            minPriority = Log.VERBOSE
            enabled = true
        }
        sinks {
            logcat()
            ringBuffer(maxEntries = 500)
        }
        redaction(DefaultRedaction())
    }
}
