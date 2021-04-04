package com.simplechat.android.framework.data.mappers

import com.simplechat.core.domain.EntityMapper
import com.simplechat.core.domain.MessageDomain
import com.simplechat.core.domain.SendingMessage
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SendingMessageMapper @Inject constructor()
    : EntityMapper<MessageDomain, SendingMessage> {

    override fun toEntity(e: SendingMessage): MessageDomain {
        return MessageDomain(
            0,
            e.chatId,
            0,
            e.text,
            e.dateSent,
            null,
            false
        )
    }

    override fun toDomain(e: MessageDomain): SendingMessage {
        throw UnsupportedOperationException()
    }

}