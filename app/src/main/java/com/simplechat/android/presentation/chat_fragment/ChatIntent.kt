package com.simplechat.android.presentation.chat_fragment

import com.simplechat.core.domain.ChatDomain
import com.simplechat.core.domain.MessageDomain
import com.simplechat.core.domain.SendingMessage

sealed class ChatIntent {
    class LoadChat(val chatId: Long): ChatIntent()
    class LoadMessages(val chatId: Long): ChatIntent()
    class SendMessage(val message: SendingMessage): ChatIntent()
    class DeleteMessage(
        val message: MessageDomain,
        val forAll: Boolean
    ): ChatIntent()
}