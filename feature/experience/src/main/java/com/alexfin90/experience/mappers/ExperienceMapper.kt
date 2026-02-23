package com.alexfin90.experience.mappers

import com.alexfin90.domain.entities.Experience
import com.alexfin90.experience.models.ExperienceUiModel

fun Experience.toUiModel() = ExperienceUiModel(
    title = this.title,
    company = this.company,
    companyLogoURl = this.companyLogoURL,
    period = this.period,
    location = this.location,
    impact = this.impact,
    keyAchievements = this.keyAchievements,
    techStack = this.techStack
)