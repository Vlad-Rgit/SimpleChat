package com.simplechat.android.presentation.home_fragment.viewmodel

import androidx.lifecycle.viewModelScope
import com.simplechat.android.presentation.home_fragment.HomeIntent
import com.simplechat.android.presentation.home_fragment.HomeState
import com.simplechat.core.mvi_base.MviViewModel
import com.simplechat.core.use_cases.home_screen.ClearCachedUser
import com.simplechat.core.use_cases.home_screen.GetLastMessages
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getLastMessages: GetLastMessages,
    private val clearCachedUser: ClearCachedUser
) : MviViewModel<HomeIntent, HomeState>() {


    override fun handleIntent(intent: HomeIntent) {
        when(intent) {
            is HomeIntent.LoadLastMessages -> handleLoadLastMessages()
            is HomeIntent.ClearCachedUser -> handleClearCachedUser()
        }
    }

    private fun handleClearCachedUser() {
        viewModelScope.launch {
            try {
                clearCachedUser.invoke()
                sendState(HomeState.UserCacheCleared)
            }
            catch (ex: Exception) {
                sendState(HomeState.Failure(ex))
            }
        }
    }

    private fun handleLoadLastMessages() {
        sendState(HomeState.Loading)
        viewModelScope.launch {
            try {
                getLastMessages().collect {
                    sendState(HomeState.LastMessages(it))
                }
            }
            catch (ex: Exception) {
                sendState(HomeState.Failure(ex))
            }
        }
    }
}