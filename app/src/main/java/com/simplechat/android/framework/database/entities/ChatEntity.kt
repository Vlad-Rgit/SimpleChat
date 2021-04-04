package com.simplechat.android.framework.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "chats")
data class ChatEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val isPrivate: Boolean,
    val photoPath: String? = null
)