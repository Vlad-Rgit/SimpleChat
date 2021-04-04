package com.simplechat.core.use_cases

import com.simplechat.core.data.UserRepository
import javax.inject.Inject

class GetCurrentUserImpl @Inject constructor(
    private val userRepository: UserRepository
): GetCurrentUser {

    override suspend fun invoke() = userRepository.getCurrentUser()

}