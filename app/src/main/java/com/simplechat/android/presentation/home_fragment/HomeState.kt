package com.simplechat.android.presentation.home_fragment

import com.simplechat.core.domain.LastMessageDomain

sealed class HomeState {
    object Loading: HomeState()
    object UserCacheCleared: HomeState()
    class LastMessages(val list: List<LastMessageDomain>): HomeState()
    class Failure(val throwable: Throwable): HomeState()
}
