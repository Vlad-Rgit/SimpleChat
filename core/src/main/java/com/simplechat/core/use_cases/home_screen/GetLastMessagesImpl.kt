package com.simplechat.core.use_cases.home_screen

import com.simplechat.core.data.ChatRepository
import com.simplechat.core.data.MessagesRepository
import com.simplechat.core.data.UserRepository
import com.simplechat.core.domain.LastMessageDomain

import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@ViewModelScoped
class GetLastMessagesImpl @Inject constructor(
    private val messagesRepository: MessagesRepository,
    private val userRepository: UserRepository
): GetLastMessages {
    override suspend fun invoke(): Flow<List<LastMessageDomain>> {
        val currentUser = userRepository.getCurrentUser()
        return messagesRepository.getLastMessages(currentUser)
    }
}