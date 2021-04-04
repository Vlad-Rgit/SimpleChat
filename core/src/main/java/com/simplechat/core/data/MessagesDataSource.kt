package com.simplechat.core.data

import com.simplechat.core.domain.ChatDomain
import com.simplechat.core.domain.LastMessageDomain
import com.simplechat.core.domain.MessageDomain
import com.simplechat.core.domain.UserDomain
import kotlinx.coroutines.flow.Flow

interface MessagesDataSource {

    fun getLastMessages(user: UserDomain): Flow<List<LastMessageDomain>>

    fun getMessagesFlow(chatId: Long, currentUserId: Long): Flow<List<MessageDomain>>

    suspend fun getMessages(chatId: Long, currentUserId: Long)
        : List<MessageDomain>

    suspend fun deleteMessage(message: MessageDomain, isForAll: Boolean)

    suspend fun sendMessage(message: MessageDomain)
}