package com.alexfin90.domain.usecases

import com.alexfin90.domain.contracts.CvRepository
import com.alexfin90.domain.entities.Cv
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveCvUseCase @Inject constructor(
    private val cvRepository: CvRepository
) {
    operator fun invoke(): Flow<Cv> {
        return cvRepository.observeCv()
    }
}