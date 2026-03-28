package com.alexfin90.domain.exception

open class NonFatalException(
    open val code: String,
    override val message: String,
) : Exception(message) {
    companion object {
        const val GENERIC_NON_FATAL_ERROR = "GENERIC_NON_FATAL_ERROR"
    }
}
