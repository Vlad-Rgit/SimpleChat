package com.simplechat.core.domain

import java.time.LocalDateTime

data class MessageDomain(
    val id: Long,
    val chatId: Long,
    val senderId: Long,
    val text: String,
    val dateSent: LocalDateTime,
    val dateRead: LocalDateTime?,
    val isDeletedForSender: Boolean
): ListItem<MessageDomain> {

    override fun areItemsTheSame(other: MessageDomain) = id == other.id

    override fun areContentsTheSame(other: MessageDomain) = this == other
}
