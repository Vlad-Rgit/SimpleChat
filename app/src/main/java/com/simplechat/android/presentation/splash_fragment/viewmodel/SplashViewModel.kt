package com.simplechat.android.presentation.splash_fragment.viewmodel

import androidx.lifecycle.viewModelScope
import com.simplechat.android.presentation.splash_fragment.SplashIntent
import com.simplechat.android.presentation.splash_fragment.SplashState
import com.simplechat.core.mvi_base.MviViewModel
import com.simplechat.core.use_cases.splash_screen.LoginCachedUser
import com.simplechat.core.use_cases.splash_screen.NoUserCachedException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val loginCachedUser: LoginCachedUser
        ) : MviViewModel<SplashIntent, SplashState>() {


    override fun handleIntent(intent: SplashIntent) {
        when(intent) {
            is SplashIntent.LoginCachedUser -> handleLoginCachedUserIntent()
        }
    }

    private fun handleLoginCachedUserIntent() {
        viewModelScope.launch {
            try {
                val user = loginCachedUser()
                sendState(SplashState.UserLogged(user))
            }
            catch (ex: NoUserCachedException) {
                sendState(SplashState.NoUserCached)
            }
            catch (ex: Exception) {
                sendState(SplashState.Failure(ex))
            }
        }
    }

}