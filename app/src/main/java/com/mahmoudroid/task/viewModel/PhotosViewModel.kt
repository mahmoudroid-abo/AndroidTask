package com.mahmoudroid.task.viewModel

import androidx.lifecycle.ViewModel
import com.mahmoudroid.domain.entity.photos.Photos
import com.mahmoudroid.domain.usecase.GetPhotoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

private const val TAG = "PhotosViewModel"

@HiltViewModel
class PhotosViewModel @Inject constructor(
    private val getPhotoUseCase: GetPhotoUseCase
) : ViewModel() {


    private suspend fun getPhotosFlow(photoId : String): Flow<Result<Photos?>> =
        getPhotoUseCase.invoke(photoId)


    suspend fun getUser(userId : String): Flow<Result<Photos??>> {
        return getPhotosFlow(userId)
    }
}