package com.simplechat.core.use_cases

import com.simplechat.core.domain.UserDomain

interface GetCurrentUser {
    suspend operator fun invoke(): UserDomain
}