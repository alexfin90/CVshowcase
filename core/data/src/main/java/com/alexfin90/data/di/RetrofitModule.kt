package com.alexfin90.data.di

import com.alexfin90.cvshowcase.core.data.BuildConfig
import com.alexfin90.data.remote.CvApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
    .baseUrl(BuildConfig.BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create())
    .build()

    @Provides
    fun provideDownloadService(retrofit: Retrofit): CvApiService =
        retrofit.create(CvApiService::class.java)

}