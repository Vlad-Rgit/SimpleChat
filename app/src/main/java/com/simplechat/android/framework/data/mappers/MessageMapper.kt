package com.simplechat.android.framework.data.mappers

import com.simplechat.android.framework.database.entities.MessageEntity
import com.simplechat.android.framework.utils.ofEpochMillisToLocalDateTime
import com.simplechat.android.framework.utils.toEpochMillis
import com.simplechat.core.domain.EntityMapper
import com.simplechat.core.domain.MessageDomain
import java.time.Instant
import java.time.ZoneId
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MessageMapper @Inject
    constructor(): EntityMapper<MessageEntity, MessageDomain> {


    override fun toEntity(e: MessageDomain): MessageEntity {
        return MessageEntity(
            id = e.id,
            chatId = e.chatId,
            senderId = e.senderId,
            text = e.text,
            dateSentMillis = e.dateSent.toEpochMillis(),
            dateReadMillis = if(e.dateRead == null) null else
                    e.dateRead!!.toEpochMillis(),
            isDeletedForSender = e.isDeletedForSender
        )
    }

    override fun toDomain(e: MessageEntity): MessageDomain {
        return MessageDomain(
            id = e.id,
            chatId = e.chatId,
            senderId = e.senderId,
            text = e.text,
            dateSent = ofEpochMillisToLocalDateTime(e.dateSentMillis),
            dateRead = if(e.dateReadMillis == null) null else
                    ofEpochMillisToLocalDateTime(e.dateReadMillis),
            isDeletedForSender = e.isDeletedForSender
        )
    }


}