package com.simplechat.android.presentation.splash_fragment

sealed class SplashIntent {
    object LoginCachedUser : SplashIntent()
}