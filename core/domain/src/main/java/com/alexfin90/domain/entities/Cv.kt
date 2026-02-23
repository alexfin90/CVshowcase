package com.alexfin90.domain.entities

data class Cv(
    val profile: Profile,
    val education: List<Education>,
    val languages: List<Language>,
    val certifications: List<Certification>,
    val coreTechnicalCompetencies: Map<String, List<String>>,
    val experience: List<Experience>
)