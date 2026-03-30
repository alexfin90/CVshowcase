package com.alexfin90.logging.timber

import com.alexfin90.logging.LogEntry
import com.alexfin90.logging.NonFatalReport
import com.alexfin90.logging.exception.NonFatalException
import com.alexfin90.logging.pipeline.LogPipeline
import timber.log.Timber

class LogwoodTimberTree(
    private val pipeline: LogPipeline,
) : Timber.Tree() {

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        val entry = LogEntry(
            priority = priority,
            tag = tag,
            message = message,
            throwable = t,
        )
        pipeline.dispatch(entry)
        if (t is NonFatalException) {
            pipeline.dispatchNonFatal(NonFatalReport(throwable = t))
        }
    }
}
