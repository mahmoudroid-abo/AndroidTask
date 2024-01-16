package com.mahmoudroid.domain.usecase

import com.mahmoudroid.domain.repo.UserRepo

class GetAlbumsUseCase(private val userRepo: UserRepo) {
    suspend operator fun invoke(userId: String) = userRepo.getUserAlbumsById(userId)
}