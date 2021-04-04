package com.simplechat.android.presentation.home_fragment

sealed class HomeIntent {
    object LoadLastMessages: HomeIntent()
    object ClearCachedUser: HomeIntent()
}
