package com.simplechat.android.presentation.login_fragment

import com.simplechat.core.domain.UserDomain

sealed class LoginState {
    object Loading : LoginState()
    object ErrorCredentials: LoginState()
    class SuccessfulLogin(val user: UserDomain): LoginState()
    class Failure(val throwable: Throwable): LoginState()
}
