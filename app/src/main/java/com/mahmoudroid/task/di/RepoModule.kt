package com.mahmoudroid.task.di

import com.mahmoudroid.data.remote.ApiService
import com.mahmoudroid.data.repo.UserRepoImpl
import com.mahmoudroid.domain.repo.UserRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {

    @Provides
    fun provideRepo(apiService: ApiService) : UserRepo{
        return UserRepoImpl(apiService)
    }
}