package com.mahmoudroid.domain.repo

import com.mahmoudroid.domain.entity.album.Album
import com.mahmoudroid.domain.entity.photos.Photos
import com.mahmoudroid.domain.entity.user.UserItem
import kotlinx.coroutines.flow.Flow

interface UserRepo {
    suspend fun getUserRequest(userId: String): Flow<Result<UserItem>>
    suspend fun getUserAlbumsById(userId: String): Flow<Result<Album>>
    suspend fun getUserPhotoById(userId: String): Flow<Result<Photos>>
}