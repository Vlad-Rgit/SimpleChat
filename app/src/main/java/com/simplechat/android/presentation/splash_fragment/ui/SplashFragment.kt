package com.simplechat.android.presentation.splash_fragment.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.simplechat.android.databinding.FragmentSplashBinding
import com.simplechat.android.presentation.splash_fragment.*
import com.simplechat.android.presentation.splash_fragment.viewmodel.SplashViewModel
import com.simplechat.core.mvi_base.MviViewFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment: MviViewFragment<
        SplashIntent, SplashState, FragmentSplashBinding>() {


    override fun getViewModelClass(): Class<SplashViewModel> {
        return SplashViewModel::class.java
    }

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSplashBinding {
        return FragmentSplashBinding.inflate(
            inflater,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sendIntent(SplashIntent.LoginCachedUser)
    }


    override fun render(state: SplashState) {
        when(state) {
            is SplashState.UserLogged -> handleUserLogged()
            is SplashState.NoUserCached -> handleNoUserCached()
            is SplashState.Failure -> handleFailure(state.throwable)
        }
    }

    private fun handleUserLogged() {
        findNavController()
            .navigate(
                SplashFragmentDirections
                    .actionSplashFragmentToHomeFragment()
            )
    }

    private fun handleNoUserCached() {
        findNavController()
            .navigate(
                SplashFragmentDirections
                    .actionSplashFragmentToLoginFragment()
            )
    }

    private fun handleFailure(throwable: Throwable) {
        Toast.makeText(
            requireContext(),
            throwable.message ?: throwable.javaClass.name,
            Toast.LENGTH_SHORT
        ).show()
    }
}