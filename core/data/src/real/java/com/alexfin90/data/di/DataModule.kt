package com.alexfin90.data.di

import com.alexfin90.domain.contracts.CvRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindCvRepository(cvRepositoryImpl: CvRepositoryImpl): CvRepository

}