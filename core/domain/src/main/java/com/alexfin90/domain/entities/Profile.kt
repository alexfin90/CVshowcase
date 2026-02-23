package com.alexfin90.domain.entities

data class Profile(
    val fullName: String,
    val title: String,
    val location: String,
    val profileImageURL: String,
    val professionalSummary: String,
    val keyStrengths: List<String>,
    val phoneNumber: String,
    val email: String,
    val linkedinURL: String,
    val githubURL: String,
    val cvAppURL: String
)