package com.alexfin90.domain.usecases

import com.alexfin90.domain.contracts.Cv2Repository
import com.alexfin90.domain.entities.Cv
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveCv2UseCase @Inject constructor(
    private val cv2Repository: Cv2Repository
) {
    operator fun invoke(): Flow<Cv> {
       return  cv2Repository.observeCv()
    }
}