package com.simplechat.android.framework.database.entities

import androidx.room.Embedded
import com.simplechat.core.domain.ChatDomain

data class ChatInfoSelectResult (
    val chatId: Long,
    val isPrivate: Boolean,
    val chatName: String,
    val displayUserName: String
)