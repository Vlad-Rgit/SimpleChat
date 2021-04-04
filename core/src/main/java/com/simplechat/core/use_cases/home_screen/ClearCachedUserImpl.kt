package com.simplechat.core.use_cases.home_screen

import com.simplechat.core.data.UserRepository
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class ClearCachedUserImpl @Inject constructor(
    private val userRepository: UserRepository
): ClearCachedUser {
    override suspend fun invoke() {
        userRepository.clearCachedUser()
    }
}