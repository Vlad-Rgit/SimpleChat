package com.simplechat.core.use_cases.chat_screen

import com.simplechat.core.data.MessagesRepository
import com.simplechat.core.domain.MessageDomain
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject


@ViewModelScoped
class DeleteMessageImpl @Inject constructor(
    private val messagesRepository: MessagesRepository
): DeleteMessage {
    override suspend fun invoke(message: MessageDomain, isForAll: Boolean) {
        messagesRepository.deleteMessage(message, isForAll)
    }
}