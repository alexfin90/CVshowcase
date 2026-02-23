package com.alexfin90.data.remote

import com.alexfin90.data.remote.dto.CvDto
import retrofit2.http.GET

interface CvApiService {
    @GET("cv.json")
    suspend fun getCv(): CvDto
}