package com.mahmoudroid.task.di

import com.mahmoudroid.domain.repo.UserRepo
import com.mahmoudroid.domain.usecase.GetAlbumsUseCase
import com.mahmoudroid.domain.usecase.GetPhotoUseCase
import com.mahmoudroid.domain.usecase.GetUserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideUserUseCase(userRepo: UserRepo): GetUserUseCase {
        return GetUserUseCase(userRepo)
    }

    @Provides
    fun provideAlbumsUseCase(userRepo: UserRepo): GetAlbumsUseCase {
        return GetAlbumsUseCase(userRepo)
    }

    @Provides
    fun providePhotosUseCase(userRepo: UserRepo): GetPhotoUseCase {
        return GetPhotoUseCase(userRepo)
    }
}