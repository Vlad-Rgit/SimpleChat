package com.simplechat.android.presentation.chat_fragment.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.simplechat.android.R
import com.simplechat.android.databinding.FragmentChatBinding
import com.simplechat.android.presentation.chat_fragment.ChatIntent
import com.simplechat.android.presentation.chat_fragment.ChatState
import com.simplechat.android.presentation.chat_fragment.viewmodel.ChatViewModel
import com.simplechat.android.presentation.views.PaddingDecorator
import com.simplechat.core.mvi_base.MviViewFragment
import com.simplechat.core.mvi_base.MviViewModel
import com.simplechat.core.domain.MessageDomain
import com.simplechat.core.domain.SendingMessage
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime


@AndroidEntryPoint
class ChatFragment: MviViewFragment<ChatIntent, ChatState, FragmentChatBinding>() {

    private lateinit var messagesAdapter: MessagesAdapter
    private var chatId: Long = -1

    override fun getViewModelClass(): Class<out MviViewModel<ChatIntent, ChatState>> {
        return ChatViewModel::class.java
    }

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentChatBinding {
        return FragmentChatBinding.inflate(
            inflater,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initButtons()
        initRecyclerView()
        initToolbar()

        chatId = ChatFragmentArgs
            .fromBundle(requireArguments())
            .chatId

        sendIntent(ChatIntent.LoadChat(chatId))
        sendIntent(ChatIntent.LoadMessages(chatId))
    }

    private fun initButtons() {
        binding.run {
            btnSend.setOnClickListener {
                val text = edMessage.text?.toString()
                if(text != null &&
                        text.isNotBlank()) {
                    sendIntent(ChatIntent.SendMessage(
                        SendingMessage(
                            chatId,
                            text,
                            LocalDateTime.now()
                        )
                    ))
                    edMessage.setText("")
                }
            }
        }
    }

    private fun initRecyclerView() {
        messagesAdapter = MessagesAdapter().apply {
            setOnMessageDeleteClicked(::handleOnDeleteMessageClicked)
        }

        binding.rvMessages.run {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext()).apply {
                stackFromEnd = true
            }
            adapter = messagesAdapter

            val padding = requireContext()
                .resources
                .getDimensionPixelSize(R.dimen.medium_padding)

            addItemDecoration(
                PaddingDecorator(
                    0, padding, 0, padding
                )
            )
        }
    }

    private fun initToolbar() {
        binding.toolbar.run {
            setNavigationOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    override fun render(state: ChatState) {
        when(state) {
            is ChatState.Loading -> handleLoading()
            is ChatState.ListMessages -> handleListMessages(state)
            is ChatState.ChatInfo -> handleChatInfo(state)
            is ChatState.Failure -> handleFailure(state)
        }
    }

    private fun handleLoading() {
        enableLoading()
    }

    private fun enableLoading() {
        binding.run {
            rvMessages.visibility = View.GONE
            progressCircular.visibility = View.VISIBLE
        }
    }

    private fun disableLoading() {
        binding.run {
            rvMessages.visibility = View.VISIBLE
            progressCircular.visibility = View.GONE
        }
    }

    private fun handleChatInfo(state: ChatState.ChatInfo) {
        val chat = state.chat
        binding.tvToolbarTitle.text = if(chat.isPrivate)
            chat.displayUserName!!
        else
            chat.chatName
    }

    private fun handleListMessages(state: ChatState.ListMessages) {
        disableLoading()
        messagesAdapter.currentUserId = state.currentUserId
        val list = state.list
        messagesAdapter.setItems(list)

        val linearManager = binding.rvMessages.layoutManager
            as LinearLayoutManager

        val lastIndex = list.size - 1

        if(linearManager.findLastCompletelyVisibleItemPosition()
            < state.list.size - 1 &&
                state.list[lastIndex].senderId == state.currentUserId) {
            binding.rvMessages.smoothScrollToPosition(lastIndex)
        }
    }

    private fun handleFailure(state: ChatState.Failure) {
        Toast.makeText(
            requireContext(),
            state.throwable.message,
            Toast.LENGTH_SHORT
        ).show()
    }


    private fun handleOnDeleteMessageClicked(message: MessageDomain) {
        AlertDialog.Builder(requireContext())
            .setMessage(R.string.delete_message_question)
            .setPositiveButton(
                R.string.delete_for_all
            ) { _, _ -> sendIntent(ChatIntent.DeleteMessage(message, true)) }
            .setNeutralButton(
                R.string.delete_for_sender
            ) { _, _ ->
                sendIntent(ChatIntent.DeleteMessage(message, false))
            }
            .setNegativeButton(R.string.cancel) { _, _ -> }
            .show()
    }
}