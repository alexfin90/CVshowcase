package com.alexfin90.data.di

import com.alexfin90.data.repositories.Cv2RepositoryImpl
import com.alexfin90.domain.contracts.Cv2Repository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class CvModule {
    @Binds
    abstract fun bindCv2Repository( cv2RepositoryImpl: Cv2RepositoryImpl) : Cv2Repository
}