package com.alexfin90.logging

data class NonFatalReport(
    val throwable: Throwable,
    val attributes: Map<String, String> = emptyMap(),
    val fingerprint: String? = null,
)
