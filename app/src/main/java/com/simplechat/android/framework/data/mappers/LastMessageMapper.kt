package com.simplechat.android.framework.data.mappers

import com.simplechat.android.framework.database.entities.ChatEntity
import com.simplechat.android.framework.database.entities.LastMessageSelectResult
import com.simplechat.android.framework.database.entities.MessageEntity
import com.simplechat.android.framework.utils.ofEpochMillisToLocalDateTime
import com.simplechat.android.framework.utils.toEpochMillis
import com.simplechat.core.domain.EntityMapper
import com.simplechat.core.domain.LastMessageDomain
import java.lang.UnsupportedOperationException
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LastMessageMapper @Inject constructor()
    : EntityMapper<LastMessageSelectResult, LastMessageDomain> {

    override fun toEntity(e: LastMessageDomain): LastMessageSelectResult {
        //LastMessageResult is read only, so we map it only to domain
        throw UnsupportedOperationException()
    }

    override fun toDomain(e: LastMessageSelectResult): LastMessageDomain {
        return LastMessageDomain(
            e.message.id,
            e.chat.id,
            e.message.senderId,
            e.chat.isPrivate,
            e.chat.name,
            e.sender.name,
            e.displayUser.name,
            e.message.text,
            e.chat.photoPath,
            ofEpochMillisToLocalDateTime(e.message.dateSentMillis),
            if(e.message.dateReadMillis == null) null
                else ofEpochMillisToLocalDateTime(e.message.dateReadMillis)
        )
    }

}