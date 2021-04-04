package com.simplechat.android.presentation.login_fragment.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.simplechat.android.R
import com.simplechat.android.databinding.FragmentLoginBinding
import com.simplechat.android.presentation.login_fragment.LoginIntent
import com.simplechat.android.presentation.login_fragment.LoginState
import com.simplechat.android.presentation.login_fragment.viewmodel.LoginViewModel
import com.simplechat.core.mvi_base.MviViewFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginFragment:
    MviViewFragment<LoginIntent, LoginState, FragmentLoginBinding>() {

    override fun getViewModelClass(): Class<LoginViewModel> {
        return LoginViewModel::class.java
    }

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentLoginBinding {
        return FragmentLoginBinding.inflate(
            inflater,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initButtons()
    }

    private fun initButtons() {
        binding.run {

            btnLogin.setOnClickListener {

                val login = edLogin.text?.toString()
                val password = edLogin.text?.toString()

                if(login != null && password != null) {
                    sendIntent(
                        LoginIntent.Enter(login, password)
                    )
                }
            }
        }
    }


    override fun render(state: LoginState) {
        when(state) {
            is LoginState.SuccessfulLogin -> handleSuccessfulLogin()
            is LoginState.Loading -> handleLoading()
            is LoginState.ErrorCredentials -> handleErrorCredentials()
            is LoginState.Failure -> handleFailure(state.throwable)
        }
    }

    private fun handleLoading() {
        clearErrors()
        setLoadingState()
    }

    private fun handleSuccessfulLogin() {
        disableLoadingState()
        clearErrors()

        findNavController().navigate(
            LoginFragmentDirections
                .actionLoginFragmentToHomeFragment()
        )
    }

    private fun handleErrorCredentials() {
        disableLoadingState()
        setErrorCredentialsError()
    }

    private fun handleFailure(throwable: Throwable) {
        Toast.makeText(
            requireContext(),
            throwable.message,
            Toast.LENGTH_SHORT
        ).show()
    }


    private fun setLoadingState() {
        binding.run {
            progressCircular.visibility = View.VISIBLE
            btnLogin.visibility = View.GONE
        }
    }

    private fun disableLoadingState() {
        binding.run {
            progressCircular.visibility = View.GONE
            btnLogin.visibility = View.VISIBLE
        }
    }

    private fun clearErrors() {
        binding.run {
            txtLayoutPassword.error = null
            txtLayoutLogin.error = null
        }
    }

    private fun setErrorCredentialsError() {
        binding.run {

            val errorText = requireContext()
                .getString(R.string.incorrect_login_or_password)

            txtLayoutLogin.error = errorText
            txtLayoutPassword.error = errorText
        }
    }


}