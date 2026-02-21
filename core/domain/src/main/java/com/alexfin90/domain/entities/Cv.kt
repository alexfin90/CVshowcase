package com.alexfin90.domain.entities

data class Cv(
    val experiences: List<Experience> = emptyList(),
    val profile : Profile = Profile(),
    val language: Language
)
