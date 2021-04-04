package com.simplechat.core.data

import com.simplechat.core.domain.*
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

interface ChatDataSource {

    suspend fun getChatInfo(chatId: Long, currentUserId: Long): ChatInfoDomain

}