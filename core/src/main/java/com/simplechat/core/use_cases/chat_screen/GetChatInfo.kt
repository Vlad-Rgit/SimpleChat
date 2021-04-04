package com.simplechat.core.use_cases.chat_screen

import com.simplechat.core.domain.ChatDomain
import com.simplechat.core.domain.ChatInfoDomain

interface GetChatInfo {
    suspend operator fun invoke(chatId: Long): ChatInfoDomain
}