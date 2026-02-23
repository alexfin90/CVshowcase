package com.alexfin90.data.di.repository

import com.alexfin90.domain.contracts.CvRepository
import com.alexfin90.domain.entities.Cv
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CvRepositoryImpl @Inject constructor() : CvRepository {


    override suspend fun getCv(): Cv {

    }

    override fun observeCv(): Flow<Cv> {

    }
}