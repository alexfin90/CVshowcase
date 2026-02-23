package com.alexfin90.data.di.repository

import com.alexfin90.data.mapper.toDomain
import com.alexfin90.data.remote.CvApiService
import com.alexfin90.domain.contracts.CvRepository
import com.alexfin90.domain.entities.Cv
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CvRepositoryImpl @Inject constructor(
    private val api: CvApiService
) : CvRepository {


    override suspend fun getCv(): Cv {
       return api.getCv().toDomain()
    }

    override fun observeCv(): Flow<Cv> = flow {
        emit(getCv())
    }
}