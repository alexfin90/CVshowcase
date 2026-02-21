package com.alexfin90.data.di.repository

import com.alexfin90.domain.contracts.CvRepository
import com.alexfin90.domain.entities.Cv
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CvRepositoryImpl @Inject constructor() : CvRepository {
    //TODO REAL IMPLEMENTATION

    override suspend fun getCv(): Cv {
        TODO("Not yet implemented")
    }

    override fun observeCv(): Flow<Cv> {
        TODO("Not yet implemented")
    }
}