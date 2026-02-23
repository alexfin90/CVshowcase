package com.alexfin90.data.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CvDto (
    val profile: ProfileDto,
    val education: List<EducationDto>,
    val languages: List<LanguageDto>,
    val certifications: List<CertificationDto>,
    val coreTechnicalCompetencies: Map<String, List<String>>,
    val experience: List<ExperienceDto>)

@JsonClass(generateAdapter = true)
data class CertificationDto (
    val name: String,
    val issuer: String,
    val type: String
)

@JsonClass(generateAdapter = true)
data class EducationDto (
    val degree: String,
    val institution: String,
    val grade: String,
    val period: String,
    val location: String,
    @field:Json(name = "logoUrl")
    val logoURL: String
)

@JsonClass(generateAdapter = true)
data class ExperienceDto (
    val title: String,
    val company: String,
    @field:Json(name = "companyLogoUrl")
    val companyLogoURL: String,
    @field:Json(name = "companyWebsiteUrl")
    val companyWebsiteURL: String,
    val team: String? = null,
    val period: String,
    val location: String,
    val impact: String,
    val keyAchievements: List<String>,
    val apps: List<AppDto>,
    val techStack: List<String>
)

@JsonClass(generateAdapter = true)
data class AppDto (
    val name: String,
    val platform: String,
    val url: String,
    val role: String
)

@JsonClass(generateAdapter = true)
data class LanguageDto (
    val name: String,
    val level: String,
    val score: Long,
    val maxScore: Long
)

@JsonClass(generateAdapter = true)
data class ProfileDto (
    val fullName: String,
    val title: String,
    val location: String,
    @field:Json(name = "profileImageUrl")
    val profileImageURL: String,
    val professionalSummary: String,
    val keyStrengths: List<String>,
    val phoneNumber: String,
    val email: String,
    @field:Json(name = "linkedinUrl")
    val linkedinURL: String,
    @field:Json(name = "githubUrl")
    val githubURL: String,
    @field:Json(name = "cvAppUrl")
    val cvAppURL: String
)