package com.simplechat.core.use_cases.chat_screen

import com.simplechat.core.domain.ChatDomain
import com.simplechat.core.domain.MessageDomain
import kotlinx.coroutines.flow.Flow

interface GetMessages {
    suspend operator fun invoke(chatId: Long)
        : Flow<List<MessageDomain>>
}