package com.simplechat.core.domain

data class ChatInfoDomain(
    val chatId: Long,
    val chatName: String,
    val isPrivate: Boolean,
    val displayUserName: String?
)