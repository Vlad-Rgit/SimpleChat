package com.simplechat.core.data

import com.simplechat.core.domain.MessageDomain
import com.simplechat.core.domain.UserDomain
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class MessagesRepository @Inject constructor(
    private val messagesDataSource: MessagesDataSource
) {
    suspend fun getMessages(chatId: Long, currentUserId: Long)
        = messagesDataSource.getMessagesFlow(chatId, currentUserId)

    suspend fun sendMessage(message: MessageDomain)
        = messagesDataSource.sendMessage(message)

    suspend fun getLastMessages(user: UserDomain)
      = messagesDataSource.getLastMessages(user)

    suspend fun deleteMessage(message: MessageDomain, isForAll: Boolean)
     = messagesDataSource.deleteMessage(message, isForAll)
}