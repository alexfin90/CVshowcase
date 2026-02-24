package com.alexfin90.domain.contracts

import com.alexfin90.domain.entities.Cv
import kotlinx.coroutines.flow.Flow

interface Cv2Repository {
    suspend fun getCv2(): Cv
    fun observeCv(): Flow<Cv>
}