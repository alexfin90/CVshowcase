package com.alexfin90.logging.exception

open class NonFatalException(
    open val code: String,
    override val message: String,
    cause: Throwable? = null,
) : Exception(message, cause)
