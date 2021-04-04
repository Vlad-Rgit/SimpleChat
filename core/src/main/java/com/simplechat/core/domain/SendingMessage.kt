package com.simplechat.core.domain

import java.time.LocalDateTime

data class SendingMessage(
    val chatId: Long,
    val text: String,
    val dateSent: LocalDateTime
)