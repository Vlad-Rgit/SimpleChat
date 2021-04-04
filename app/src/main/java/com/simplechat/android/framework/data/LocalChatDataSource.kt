package com.simplechat.android.framework.data

import com.simplechat.android.framework.database.SimpleChatDatabase
import com.simplechat.android.framework.database.entities.*
import com.simplechat.core.data.ChatDataSource
import com.simplechat.core.data.UserDataSource
import com.simplechat.core.domain.*
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDateTime
import javax.inject.Inject

@ViewModelScoped
class LocalChatDataSource @Inject constructor(
    private val chatInfoDomainMapper: EntityMapper<ChatInfoSelectResult, ChatInfoDomain>,
    private val database: SimpleChatDatabase,
) : ChatDataSource {

    override suspend fun getChatInfo(chatId: Long, currentUserId: Long): ChatInfoDomain {
        val entity = database.chatDao.getInfoById(chatId, currentUserId)
        return chatInfoDomainMapper.toDomain(entity)
    }

}