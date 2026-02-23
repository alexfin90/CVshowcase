package com.alexfin90.data.repository

import com.alexfin90.domain.contracts.CvRepository
import com.alexfin90.domain.entities.App
import com.alexfin90.domain.entities.Certification
import com.alexfin90.domain.entities.Cv
import com.alexfin90.domain.entities.Education
import com.alexfin90.domain.entities.Experience
import com.alexfin90.domain.entities.Language
import com.alexfin90.domain.entities.Profile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject


class CvRepositoryMockImpl @Inject constructor() : CvRepository {

    private var cv = Cv(
        profile = Profile(
            fullName = "Alex Finocchiaro",
            title = "Senior Android Developer",
            location = "Milan, Italy",
            profileImageURL = "https://cvshowcase-c7ebb.web.app/alex.jpeg",
            professionalSummary = "Experienced Android developer with 6+ years of expertise in Kotlin, Jetpack Compose, and modern Android architecture patterns. Passionate about building high-quality, user-centric mobile applications.",
            keyStrengths = listOf(
                "Kotlin & Jetpack Compose",
                "Clean Architecture",
                "MVVM & MVI Patterns",
                "Coroutines & Flow",
                "Firebase Integration",
                "RESTful APIs"
            ),
            phoneNumber = "+39 123 456 7890",
            email = "alex@example.com",
            linkedinURL = "https://linkedin.com/in/alexfin90",
            githubURL = "https://github.com/alexfin90",
            cvAppURL = "https://cvshowcase.com"
        ),
        education = listOf(
            Education(
                degree = "Bachelor of Science in Computer Science",
                institution = "Università degli Studi di Milano",
                grade = "110/110 cum laude",
                period = "2016 - 2019",
                location = "Milan, Italy",
                logoURL = "https://cvshowcase-c7ebb.web.app/logos/unimi.png"
            )
        ),
        languages = listOf(
            Language(
                name = "Italian",
                level = "Native",
                score = 5,
                maxScore = 5
            ),
            Language(
                name = "English",
                level = "Fluent",
                score = 5,
                maxScore = 5
            ),
            Language(
                name = "Spanish",
                level = "Intermediate",
                score = 3,
                maxScore = 5
            )
        ),
        certifications = listOf(
            Certification(
                name = "Associate Android Developer",
                issuer = "Google",
                type = "Mobile Development"
            ),
            Certification(
                name = "Professional Cloud Architect",
                issuer = "Google Cloud",
                type = "Cloud"
            ),
            Certification(
                name = "Certified ScrumMaster (CSM)",
                issuer = "Scrum Alliance",
                type = "Agile"
            )
        ),
        coreTechnicalCompetencies = mapOf(
            "languages" to listOf("Kotlin", "Java", "Python", "TypeScript", "SQL"),
            "uiFrameworks" to listOf("Jetpack Compose", "Material Design 3", "Android Views", "Flutter"),
            "architectureAndPatterns" to listOf("Clean Architecture", "MVVM", "MVI", "Repository Pattern", "Dependency Injection"),
            "concurrency" to listOf("Coroutines", "Flow", "LiveData", "StateFlow"),
            "testing" to listOf("Unit Testing", "Integration Testing", "UI Testing", "JUnit", "Mockito"),
            "tools" to listOf("Android Studio", "Git", "Gradle", "Firebase", "Docker")
        ),
        experience = listOf(
            Experience(
                title = "Senior Android Developer",
                company = "Google",
                companyLogoURL = "https://www.gstatic.com/images/branding/product/1x/googleg_120.png",
                companyWebsiteURL = "https://www.google.com",
                team = "Android Developer Experience",
                period = "2023 - Present",
                location = "Milan, Italy (Remote)",
                impact = "Led development of critical Android features used by 2M+ users",
                keyAchievements = listOf(
                    "Architected and implemented new Compose-based UI framework reducing code complexity by 30%",
                    "Reduced app startup time by 40% through optimization and profiling",
                    "Mentored 5 junior developers on Kotlin best practices and clean architecture",
                    "Improved test coverage from 45% to 85% through comprehensive testing strategy"
                ),
                apps = listOf(
                    App(
                        name = "Google Play Services",
                        platform = "Android",
                        url = "https://play.google.com/store/apps/details?id=com.google.android.gms",
                        role = "Senior Developer"
                    )
                ),
                techStack = listOf("Kotlin", "Jetpack Compose", "Coroutines", "Flow", "Firebase", "Clean Architecture", "MVVM")
            ),
            Experience(
                title = "Android Developer",
                company = "Technogym",
                companyLogoURL = "https://www.technogym.com/content/dam/technogym/images/logo.svg",
                companyWebsiteURL = "https://www.technogym.com",
                team = "Mobile Apps",
                period = "2021 - 2023",
                location = "Cesena, Italy",
                impact = "Developed fitness tracking app with 500K+ active users and 4.8 star rating",
                keyAchievements = listOf(
                    "Built real-time workout tracking with Bluetooth connectivity for fitness equipment",
                    "Implemented offline-first architecture using Room database and Sync adapter",
                    "Increased user engagement by 35% through UI/UX improvements and gamification",
                    "Reduced crash rate by 60% through comprehensive error handling and crash reporting"
                ),
                apps = listOf(
                    App(
                        name = "Technogym App",
                        platform = "Android",
                        url = "https://play.google.com/store/apps/details?id=com.technogym.app",
                        role = "Lead Developer"
                    )
                ),
                techStack = listOf("Kotlin", "MVVM", "Room", "Coroutines", "Retrofit", "Material Design", "Firebase")
            ),
            Experience(
                title = "Junior Android Developer",
                company = "Acme Solutions",
                companyLogoURL = "https://example.com/acme-logo.png",
                companyWebsiteURL = "https://example.com",
                team = "Mobile Development",
                period = "2019 - 2021",
                location = "Milan, Italy",
                impact = "Contributed to development of 5+ enterprise applications",
                keyAchievements = listOf(
                    "Developed and maintained 3 enterprise applications with millions of downloads",
                    "Implemented RESTful API integrations using Retrofit and proper networking patterns",
                    "Collaborated with UX team to improve user experience and reduce support tickets",
                    "Participated in code reviews and learned best practices in mobile development"
                ),
                apps = listOf(
                    App(
                        name = "Acme Enterprise App",
                        platform = "Android",
                        url = "",
                        role = "Developer"
                    )
                ),
                techStack = listOf("Java", "Kotlin", "Android SDK", "SQLite", "Retrofit", "Material Design")
            )
        )
    )

    override suspend fun getCv(): Cv {
        return cv
    }

    override fun observeCv(): Flow<Cv> {
        return flowOf(value = cv)
    }
}