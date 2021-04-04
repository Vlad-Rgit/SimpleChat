package com.simplechat.core.domain

import java.time.LocalDateTime

data class LastMessageDomain(
    val messageId: Long,
    val chatId: Long,
    val senderId: Long,
    val isChatPrivate: Boolean,
    val chatName: String,
    val senderName: String,
    val displayUserName: String,
    val text: String,
    val photoPath: String?,
    val dateSent: LocalDateTime,
    val dateRead: LocalDateTime?
): ListItem<LastMessageDomain> {
    override fun areItemsTheSame(other: LastMessageDomain) = messageId == other.messageId
    override fun areContentsTheSame(other: LastMessageDomain) = this == other
}