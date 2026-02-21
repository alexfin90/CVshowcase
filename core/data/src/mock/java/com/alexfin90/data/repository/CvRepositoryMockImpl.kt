package com.alexfin90.data.repository

import com.alexfin90.domain.contracts.CvRepository
import com.alexfin90.domain.entities.Cv
import com.alexfin90.domain.entities.Experience
import com.alexfin90.domain.entities.Language
import com.alexfin90.domain.entities.Profile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject


class CvRepositoryMockImpl @Inject constructor() : CvRepository {

    private val cv = Cv(
        experiences = listOf(Experience(
            title = "Senior Android Developer",
            company = "Google",
        ),Experience(
            title = "Android Developer",
            company = "Technogym",
        )),
        profile = Profile(),
        language = Language()
    )

    override suspend fun getCv(): Cv {
        return cv
    }

    override fun observeCv(): Flow<Cv> {
        return flowOf(value = cv)
    }
}