package com.alexfin90.data.repositories

import com.alexfin90.data.mapper.toDomain
import com.alexfin90.data.remote.CvApiService
import com.alexfin90.domain.contracts.Cv2Repository
import com.alexfin90.domain.entities.Cv
import com.technogym.android.absolute.strength.core.didispatcher.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject


class Cv2RepositoryImpl @Inject constructor(
    @param:IoDispatcher private val ioDispatcher : CoroutineDispatcher,
    private val api : CvApiService
) : Cv2Repository {
    override suspend fun getCv2(): Cv {
       return withContext(ioDispatcher){
            api.getCv().toDomain()
        }

    }
    override fun observeCv(): Flow<Cv> = flow {
        emit(getCv2())
    }
}