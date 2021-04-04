package com.simplechat.core.use_cases.chat_screen

import com.simplechat.core.data.MessagesRepository
import com.simplechat.core.data.UserRepository
import com.simplechat.core.domain.EntityMapper
import com.simplechat.core.domain.MessageDomain
import com.simplechat.core.domain.SendingMessage
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject
import javax.inject.Singleton

@ViewModelScoped
class SendMessageImpl @Inject constructor(
    private val sendingMessageMapper: EntityMapper<MessageDomain, SendingMessage>,
    private val messagesRepository: MessagesRepository,
    private val userRepository: UserRepository
): SendMessage {
    override suspend fun invoke(message: SendingMessage) {

        val domain = sendingMessageMapper.toEntity(message)
        val currentUser = userRepository.getCurrentUser()

        messagesRepository.sendMessage(
            MessageDomain(
            0,
            domain.chatId,
            currentUser.id,
            domain.text,
            domain.dateSent,
            domain.dateRead,
                domain.isDeletedForSender
        )
        )
    }

}