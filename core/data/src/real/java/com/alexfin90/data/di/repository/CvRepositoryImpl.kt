package com.alexfin90.data.di.repository

import com.alexfin90.data.mapper.toDomain
import com.alexfin90.data.remote.CvApiService
import com.alexfin90.domain.contracts.CvRepository
import com.alexfin90.domain.entities.Cv
import com.technogym.android.absolute.strength.core.didispatcher.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CvRepositoryImpl @Inject constructor(
    @param:IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val api: CvApiService
) : CvRepository {


    override suspend fun getCv(): Cv {
       return withContext(dispatcher){
            api.getCv().toDomain()
       }
    }

    override fun observeCv(): Flow<Cv> = flow {
        emit(getCv())
    }
}