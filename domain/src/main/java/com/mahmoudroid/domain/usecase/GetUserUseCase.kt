package com.mahmoudroid.domain.usecase

import com.mahmoudroid.domain.repo.UserRepo

class GetUserUseCase(private val userRepo: UserRepo) {

    suspend operator fun invoke(userId : String) = userRepo.getUserRequest(userId)
}