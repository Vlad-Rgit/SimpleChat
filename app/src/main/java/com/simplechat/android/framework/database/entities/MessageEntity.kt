package com.simplechat.android.framework.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "messages")
data class MessageEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ForeignKey(
        entity = ChatEntity::class,
        parentColumns = ["id"],
        childColumns = ["chatId"],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    )
    val chatId: Long,
    @ForeignKey(
        entity = UserEntity::class,
        parentColumns = ["id"],
        childColumns = ["senderId"],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    )
    val senderId: Long,
    val text: String,
    val dateSentMillis: Long,
    val dateReadMillis: Long? = null,
    val isDeletedForSender: Boolean = false
)
