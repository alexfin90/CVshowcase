package com.alexfin90.data.remote

import retrofit2.http.GET

interface CvApiService {
    @GET("cv.json")
    suspend fun getCv(): List<CvDto>
}