package com.simplechat.core.use_cases.chat_screen

import com.simplechat.core.domain.MessageDomain
import com.simplechat.core.domain.SendingMessage

interface SendMessage {
    suspend operator fun invoke(message: SendingMessage)
}