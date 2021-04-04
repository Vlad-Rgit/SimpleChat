package com.simplechat.core.use_cases.chat_screen

import com.simplechat.core.data.ChatRepository
import com.simplechat.core.data.UserRepository
import com.simplechat.core.domain.ChatDomain
import com.simplechat.core.domain.ChatInfoDomain
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class GetChatInfoImpl @Inject constructor(
    private val chatRepository: ChatRepository,
    private val userRepository: UserRepository
): GetChatInfo {

    override suspend fun invoke(chatId: Long): ChatInfoDomain {
        val currentUserId = userRepository
            .getCurrentUser().id
        return chatRepository.getChat(chatId, currentUserId)
    }
}