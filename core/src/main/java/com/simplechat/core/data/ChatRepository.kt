package com.simplechat.core.data

import com.simplechat.core.domain.UserDomain
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class ChatRepository @Inject constructor(
    private val chatDataSource: ChatDataSource
) {

    suspend fun getChat(chatId: Long, currentUserId: Long)
        = chatDataSource.getChatInfo(chatId, currentUserId)
}