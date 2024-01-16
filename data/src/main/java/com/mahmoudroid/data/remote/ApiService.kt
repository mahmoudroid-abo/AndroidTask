package com.mahmoudroid.data.remote

import com.mahmoudroid.domain.entity.album.Album
import com.mahmoudroid.domain.entity.photos.Photos
import com.mahmoudroid.domain.entity.user.UserItem
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("/users/{UserId}")
    suspend fun getUsers(@Path("UserId") userId: String): UserItem

    @GET("/albums")
    suspend fun getAlbumByUserId(@Query("userId") userId: String): Album

    @GET("/photos")
    suspend fun getPhotoByAlbumId(@Query("albumId") albumId: String):Photos
}