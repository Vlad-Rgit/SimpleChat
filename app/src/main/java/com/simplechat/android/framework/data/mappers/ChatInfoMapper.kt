package com.simplechat.android.framework.data.mappers

import com.simplechat.android.framework.database.entities.ChatInfoSelectResult
import com.simplechat.core.domain.ChatInfoDomain
import com.simplechat.core.domain.EntityMapper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChatInfoMapper @Inject constructor()
    : EntityMapper<ChatInfoSelectResult, ChatInfoDomain> {

    override fun toEntity(e: ChatInfoDomain): ChatInfoSelectResult {
        throw UnsupportedOperationException()
    }

    override fun toDomain(e: ChatInfoSelectResult): ChatInfoDomain {
        return ChatInfoDomain(
            e.chatId,
            e.chatName,
            e.isPrivate,
            e.displayUserName
        )
    }

}