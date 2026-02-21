package com.alexfin90.domain.usecases

import com.alexfin90.domain.contracts.CvRepository
import com.alexfin90.domain.entities.Cv

import javax.inject.Inject

class GetCvUseCases @Inject constructor(
    private val cvRepository: CvRepository
) {

    suspend operator fun invoke(): Result<Cv> {
        return Result.success(cvRepository.getCv())
    }
}