package com.alexfin90.domain.contracts

import com.alexfin90.domain.entities.Cv
import kotlinx.coroutines.flow.Flow

interface CvRepository {
    suspend fun getCv(): Cv
    fun observeCv(): Flow<Cv>
}