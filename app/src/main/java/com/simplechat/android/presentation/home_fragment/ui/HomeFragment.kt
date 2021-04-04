package com.simplechat.android.presentation.home_fragment.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.simplechat.android.R
import com.simplechat.android.databinding.FragmentHomeBinding
import com.simplechat.android.presentation.home_fragment.HomeIntent
import com.simplechat.android.presentation.home_fragment.HomeState
import com.simplechat.android.presentation.home_fragment.viewmodel.HomeViewModel
import com.simplechat.core.mvi_base.MviViewFragment
import com.simplechat.core.domain.LastMessageDomain
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment: MviViewFragment<
        HomeIntent, HomeState, FragmentHomeBinding>() {


    private lateinit var lastMessagesAdapter: LastMessagesAdapter

    override fun getViewModelClass(): Class<HomeViewModel> {
        return HomeViewModel::class.java
    }

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(
            inflater,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initToolbar()
        sendIntent(HomeIntent.LoadLastMessages)
    }

    private fun initToolbar() {
        binding.toolbar.setOnMenuItemClickListener {
            if(it.itemId == R.id.menu_exit_from_account) {
                handleExitFromAccount()
            }
            false
        }
    }

    private fun initRecyclerView() {

        lastMessagesAdapter = LastMessagesAdapter().apply {
            setOnLastMessageClicked(::handleMessageClicked)
        }

        binding.rvLastMessages.run {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = lastMessagesAdapter

            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
        }

    }

    override fun render(state: HomeState) {
        when(state) {
            is HomeState.Loading -> handleLoading()
            is HomeState.LastMessages -> handleLastMessages(state)
            is HomeState.Failure -> handleFailure(state.throwable)
            is HomeState.UserCacheCleared -> handleUserCacheCleared()
        }
    }

    private fun enableLoading() {
        binding.run {
            rvLastMessages.visibility = View.GONE
            progressCircular.visibility = View.VISIBLE
        }
    }

    private fun disableLoading() {
        binding.run {
            rvLastMessages.visibility = View.VISIBLE
            progressCircular.visibility = View.GONE
        }
    }

    private fun handleUserCacheCleared() {
        findNavController().navigate(
            HomeFragmentDirections
                .actionHomeFragmentToLoginFragment()
        )
    }

    private fun handleMessageClicked(lastMessageDomain: LastMessageDomain) {
        findNavController().navigate(
            HomeFragmentDirections
                .actionHomeFragmentToChatFragment(
                    lastMessageDomain.chatId
                )
        )
    }

    private fun handleLoading() {
        enableLoading()
    }

    private fun handleExitFromAccount() {
        sendIntent(HomeIntent.ClearCachedUser)
    }

    private fun handleLastMessages(state: HomeState.LastMessages) {
        disableLoading()
        lastMessagesAdapter.setItems(state.list)
    }

    private fun handleFailure(throwable: Throwable) {
        Toast.makeText(
            requireContext(),
            throwable.message,
            Toast.LENGTH_SHORT
        ).show()
    }
}