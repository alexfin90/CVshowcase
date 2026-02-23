package com.alexfin90.domain.entities


data class Experience(
    val title: String,
    val company: String,
    val companyLogoURL: String,
    val companyWebsiteURL: String,
    val team: String?,
    val period: String,
    val location: String,
    val impact: String,
    val keyAchievements: List<String>,
    val apps: List<App>,
    val techStack: List<String>
)