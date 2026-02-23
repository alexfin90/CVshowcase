package com.alexfin90.data.mapper

import com.alexfin90.data.remote.dto.AppDto
import com.alexfin90.data.remote.dto.CertificationDto
import com.alexfin90.data.remote.dto.CvDto
import com.alexfin90.data.remote.dto.EducationDto
import com.alexfin90.data.remote.dto.ExperienceDto
import com.alexfin90.data.remote.dto.LanguageDto
import com.alexfin90.data.remote.dto.ProfileDto
import com.alexfin90.domain.entities.App
import com.alexfin90.domain.entities.Certification
import com.alexfin90.domain.entities.Cv
import com.alexfin90.domain.entities.Education
import com.alexfin90.domain.entities.Experience
import com.alexfin90.domain.entities.Language
import com.alexfin90.domain.entities.Profile

fun CvDto.toDomain() = Cv(
    profile = profile.toDomain(),
    education = education.map { it.toDomain() },
    languages = languages.map { it.toDomain() },
    certifications = certifications.map { it.toDomain() },
    coreTechnicalCompetencies = coreTechnicalCompetencies,
    experience = experience.map { it.toDomain() }
)

fun ProfileDto.toDomain() = Profile(
    fullName = fullName,
    title = title,
    location = location,
    profileImageURL = profileImageURL,
    professionalSummary = professionalSummary,
    keyStrengths = keyStrengths,
    phoneNumber = phoneNumber,
    email = email,
    linkedinURL = linkedinURL,
    githubURL = githubURL,
    cvAppURL = cvAppURL
)

fun EducationDto.toDomain() = Education(
    degree = degree,
    institution = institution,
    grade = grade,
    period = period,
    location = location,
    logoURL = logoURL
)

fun LanguageDto.toDomain() = Language(
    name = name,
    level = level,
    score = score,
    maxScore = maxScore
)

fun CertificationDto.toDomain() = Certification(
    name = name,
    issuer = issuer,
    type = type
)

fun ExperienceDto.toDomain() = Experience(
    title = title,
    company = company,
    companyLogoURL = companyLogoURL,
    companyWebsiteURL = companyWebsiteURL,
    team = team,
    period = period,
    location = location,
    impact = impact,
    keyAchievements = keyAchievements,
    apps = apps.map { it.toDomain() },
    techStack = techStack
)

fun AppDto.toDomain() = App(
    name = name,
    platform = platform,
    url = url,
    role = role
)