package com.alexfin90.profile

data class ProfileScreenState(
    val isLoading : Boolean = false,
    val error : String? = null,
    val query : String = "",
    val items : List<String> = emptyList(),
    val filterItems : List<String> = emptyList()
)