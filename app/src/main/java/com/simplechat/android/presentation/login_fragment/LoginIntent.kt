package com.simplechat.android.presentation.login_fragment

sealed class LoginIntent {
    class Enter(
        val login: String,
        val password: String
    ) : LoginIntent()
}
