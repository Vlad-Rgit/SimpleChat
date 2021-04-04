package com.simplechat.android.presentation.chat_fragment.viewmodel

import androidx.lifecycle.viewModelScope
import com.simplechat.android.presentation.chat_fragment.ChatIntent
import com.simplechat.android.presentation.chat_fragment.ChatState
import com.simplechat.core.mvi_base.MviViewModel
import com.simplechat.core.use_cases.GetCurrentUser
import com.simplechat.core.use_cases.chat_screen.DeleteMessage
import com.simplechat.core.use_cases.chat_screen.GetChatInfo
import com.simplechat.core.use_cases.chat_screen.GetMessages
import com.simplechat.core.use_cases.chat_screen.SendMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject


@HiltViewModel
class ChatViewModel @Inject constructor(
    private val getMessages: GetMessages,
    private val getChat: GetChatInfo,
    private val getCurrentUser: GetCurrentUser,
    private val sendMessage: SendMessage,
    private val deleteMessage: DeleteMessage
): MviViewModel<ChatIntent, ChatState>() {

    private var chatId: Long = -1

    override fun handleIntent(intent: ChatIntent) {
        when(intent) {
            is ChatIntent.LoadMessages -> handleLoadMessages(intent)
            is ChatIntent.LoadChat -> handleLoadChat(intent)
            is ChatIntent.SendMessage -> handleSendMessage(intent)
            is ChatIntent.DeleteMessage -> handleDeleteMessage(intent)
        }
    }

    private fun handleDeleteMessage(intent: ChatIntent.DeleteMessage) {
        viewModelScope.launch {
            sendFailureOnCatch {
                deleteMessage(intent.message, intent.forAll)
            }
        }
    }

    private fun handleSendMessage(intent: ChatIntent.SendMessage) {
        viewModelScope.launch {
            sendFailureOnCatch {
                sendMessage(intent.message)
            }
        }
    }

    private fun handleLoadChat(intent: ChatIntent.LoadChat) {
        chatId = intent.chatId
        viewModelScope.launch {
            sendFailureOnCatch {
                val chat = getChat(intent.chatId)
                sendState(ChatState.ChatInfo(chat))
            }
        }
    }

    private fun handleLoadMessages(intent: ChatIntent.LoadMessages) {
        sendState(ChatState.Loading)
        viewModelScope.launch {
            sendFailureOnCatch {
                val currentUser = getCurrentUser()
                getMessages(intent.chatId).collect {
                    sendState(ChatState.ListMessages(it, currentUser.id))
                }
            }
        }
    }

    private inline fun sendFailureOnCatch(block: () -> Unit) {
        try {
            block()
        }
        catch (ex: Exception) {
            sendState(ChatState.Failure(ex))
        }
    }
}