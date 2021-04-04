package com.simplechat.core.use_cases.chat_screen

import com.simplechat.core.domain.MessageDomain

interface DeleteMessage {
    suspend operator fun invoke(message: MessageDomain, isForAll: Boolean)
}