package com.simplechat.android.presentation.login_fragment.viewmodel


import androidx.lifecycle.viewModelScope
import com.simplechat.android.presentation.login_fragment.LoginIntent
import com.simplechat.android.presentation.login_fragment.LoginState
import com.simplechat.core.mvi_base.MviViewModel
import com.simplechat.core.use_cases.login_screen.ErrorCredentialsException
import com.simplechat.core.use_cases.login_screen.LoginUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel
    @Inject constructor(
      private val loginUseCase: LoginUser
    ) : MviViewModel<LoginIntent, LoginState>() {


    override fun handleIntent(intent: LoginIntent) {
        when(intent) {
            is LoginIntent.Enter -> handleEnterIntent(intent)
        }
    }


    private fun handleEnterIntent(intent: LoginIntent.Enter) {
        viewModelScope.launch {
            try {
                val user = loginUseCase(intent.login, intent.password)
                sendState(LoginState.SuccessfulLogin(user))
            }
            catch (ex: ErrorCredentialsException) {
                sendState(LoginState.ErrorCredentials)
            }
            catch (ex: Exception) {
                sendState(LoginState.Failure(ex))
            }
        }
    }


}