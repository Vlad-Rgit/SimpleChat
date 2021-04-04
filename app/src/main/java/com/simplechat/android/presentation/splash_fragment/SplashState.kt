package com.simplechat.android.presentation.splash_fragment

import com.simplechat.core.domain.UserDomain

sealed class SplashState {
    object NoUserCached : SplashState()
    class UserLogged(val user: UserDomain) : SplashState()
    class Failure(val throwable: Throwable) : SplashState()
}