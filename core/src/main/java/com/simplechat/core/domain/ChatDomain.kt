package com.simplechat.core.domain

data class ChatDomain(
    val id: Long,
    val name: String,
    val isPrivate: Boolean,
    val photoPath: String?
)