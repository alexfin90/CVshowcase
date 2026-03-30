package com.alexfin90.logging

interface CrashReporterSink : LogSink {
    fun reportNonFatal(report: NonFatalReport)
}
