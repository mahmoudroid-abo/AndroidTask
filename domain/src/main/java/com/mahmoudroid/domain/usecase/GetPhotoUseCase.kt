package com.mahmoudroid.domain.usecase

import com.mahmoudroid.domain.repo.UserRepo

class GetPhotoUseCase (private val userRepo: UserRepo) {
    suspend operator fun invoke(userId : String) = userRepo.getUserPhotoById(userId)
}