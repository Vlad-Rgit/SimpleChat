package com.simplechat.android.framework.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.simplechat.android.framework.database.entities.ChatEntity
import com.simplechat.android.framework.database.entities.ChatInfoSelectResult
import com.simplechat.android.framework.database.entities.UserChatCrossRefEntity
import com.simplechat.android.framework.database.entities.UserEntity
import com.simplechat.core.domain.UserDomain


@Dao
interface ChatDao {

    @Query("Select * from chats")
    suspend fun getAll(): List<ChatEntity>

    @Query("""Select 
         chatId,
         chat.isPrivate as isPrivate,
         chat.name as chatName,
         user.name as displayUserName
         from userInChat
         inner join chats chat on chat.id = chatId
         inner join users user on user.id = userId
         where chatId = :chatId and userId != :currentUserId""")
    suspend fun getInfoById(chatId: Long, currentUserId: Long): ChatInfoSelectResult

    @Insert
    suspend fun insert(chat: ChatEntity): Long

    @Insert
    suspend fun addUsersToChat(usersInChat: Iterable<UserChatCrossRefEntity>)

    @Transaction
    suspend fun insertAndAddUsers(
        chat: ChatEntity,
        users: List<UserEntity>
    ) {
        val chatId = insert(chat)
        addUsersToChat(users.map {
            UserChatCrossRefEntity(
                userId = it.id,
                chatId = chatId
            )
        })
    }


}