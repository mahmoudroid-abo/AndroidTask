package com.mahmoudroid.data.repo

import com.mahmoudroid.data.remote.ApiService
import com.mahmoudroid.domain.entity.album.Album
import com.mahmoudroid.domain.entity.photos.Photos
import com.mahmoudroid.domain.entity.user.UserItem
import com.mahmoudroid.domain.repo.UserRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

private const val TAG = "UserRepoImpl"

class UserRepoImpl(private val apiService: ApiService) : UserRepo {

    override suspend fun getUserRequest(userId: String) : Flow<Result<UserItem>> = flow {
        val response = apiService.getUsers(userId)
        emit(Result.success(response))
    }.catch {
        emit(Result.failure(it))
    }

    override suspend fun getUserAlbumsById(userId: String): Flow<Result<Album>> = flow{
        val response = apiService.getAlbumByUserId(userId)
        emit(Result.success(response))
    }.catch {
        emit(Result.failure(it))
    }

    override suspend fun getUserPhotoById(userId: String): Flow<Result<Photos>> = flow{
        val response = apiService.getPhotoByAlbumId(userId)
        emit(Result.success(response))
    }.catch {
        emit(Result.failure(it))
    }

}