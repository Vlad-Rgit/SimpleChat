package com.simplechat.android.presentation.chat_fragment

import com.simplechat.core.domain.ChatDomain
import com.simplechat.core.domain.ChatInfoDomain
import com.simplechat.core.domain.MessageDomain

sealed class ChatState {
    class ListMessages(val list: List<MessageDomain>, val currentUserId: Long): ChatState()
    class ChatInfo(val chat: ChatInfoDomain): ChatState()
    object Loading: ChatState()
    class Failure(val throwable: Throwable): ChatState()
}