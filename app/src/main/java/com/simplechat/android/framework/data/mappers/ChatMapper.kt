package com.simplechat.android.framework.data.mappers

import com.simplechat.android.framework.database.entities.ChatEntity
import com.simplechat.core.domain.ChatDomain
import com.simplechat.core.domain.EntityMapper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChatMapper @Inject
    constructor(): EntityMapper<ChatEntity, ChatDomain> {

    override fun toEntity(e: ChatDomain): ChatEntity {
        return ChatEntity(
            id = e.id,
            isPrivate = e.isPrivate,
            name = e.name,
            photoPath = e.photoPath
        )
    }

    override fun toDomain(e: ChatEntity): ChatDomain {
        return ChatDomain(
            id = e.id,
            isPrivate = e.isPrivate,
            name = e.name,
            photoPath = e.photoPath
        )
    }

}