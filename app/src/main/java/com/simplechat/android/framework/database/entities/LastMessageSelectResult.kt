package com.simplechat.android.framework.database.entities

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.simplechat.core.domain.UserDomain

data class LastMessageSelectResult(
    @Embedded(prefix = "m_")
    val message: MessageEntity,
    @Embedded(prefix = "c_")
    val chat: ChatEntity,
    @Embedded(prefix = "s_")
    val sender: UserEntity,
    @Embedded(prefix = "d_")
    val displayUser: UserEntity
)
