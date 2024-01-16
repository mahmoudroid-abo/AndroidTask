package com.mahmoudroid.task.viewModel

import androidx.lifecycle.ViewModel
import com.mahmoudroid.domain.entity.album.Album
import com.mahmoudroid.domain.entity.user.UserItem
import com.mahmoudroid.domain.usecase.GetAlbumsUseCase
import com.mahmoudroid.domain.usecase.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

private const val TAG = "UserViewModel"

@HiltViewModel
class UserViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val getAlbumsUseCase: GetAlbumsUseCase
) : ViewModel() {

    private suspend fun getUserFlow(userId : String): Flow<Result<UserItem>> =
        getUserUseCase.invoke(userId)


    suspend fun getUser(userId : String): Flow<Result<UserItem?>> {
        return getUserFlow(userId)
    }

    private suspend fun getAlbumsFlow(userId : String): Flow<Result<Album?>> =
        getAlbumsUseCase.invoke(userId)


    suspend fun getAlbums(userId : String): Flow<Result<Album?>> {
        return getAlbumsFlow(userId)
    }


//    private val _users: MutableStateFlow<UserItem?> = MutableStateFlow(null)
//    val users : StateFlow<UserItem?> = _users
//
//    private val _albums: MutableStateFlow<Album?> = MutableStateFlow(null)
//    val albums : StateFlow<Album?> = _albums

//    fun getUsers(userId : String) {
//        viewModelScope.launch {
//            try {
//                _users.value = getUserUseCase(userId)
//                Log.e(TAG, "getUsers: " + _users.value.toString())
//            } catch (e: Exception) {
//                Log.e(TAG, "getUsers: " + e.message)
//            }
//        }
//    }
//
//
//    fun getAlbums(userId : String) {
//        viewModelScope.launch {
//            try {
//                _albums.value = getAlbumsUseCase(userId)
//                Log.e(TAG, "getUsers: " + _albums.value.toString())
//            } catch (e: Exception) {
//                Log.e(TAG, "getUsers: " + e.message)
//            }
//        }
//    }
}