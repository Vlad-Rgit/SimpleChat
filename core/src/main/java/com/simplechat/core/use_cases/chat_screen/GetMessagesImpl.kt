package com.simplechat.core.use_cases.chat_screen

import com.simplechat.core.data.MessagesRepository
import com.simplechat.core.data.UserRepository
import com.simplechat.core.domain.ChatDomain
import com.simplechat.core.domain.MessageDomain
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class GetMessagesImpl @Inject constructor(
    private val messagesRepository: MessagesRepository,
    private val userRepository: UserRepository
): GetMessages {
    override suspend fun invoke(chatId: Long): Flow<List<MessageDomain>> {
        val currentUser = userRepository.getCurrentUser()
        return messagesRepository.getMessages(chatId, currentUser.id)
    }
}