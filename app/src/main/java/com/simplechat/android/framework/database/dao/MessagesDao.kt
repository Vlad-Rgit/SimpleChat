package com.simplechat.android.framework.database.dao

import androidx.room.*
import com.simplechat.android.framework.database.entities.LastMessageSelectResult
import com.simplechat.android.framework.database.entities.MessageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MessagesDao {

    @Query("""Select * from(Select
                d.id as d_id,
                 d.login as d_login,
                 d.password as d_password,
                 d.name as d_name,
                * from (
                     Select 
                     m.id as m_id,
                     m.chatId as m_chatId,
                     m.senderId as m_senderId,
                     m.text as m_text,
                     m.dateSentMillis as m_dateSentMillis,
                     m.dateReadMillis as m_dateReadMillis,
                     m.isDeletedForSender as m_isDeletedForSender,
                     c.id as c_id,
                     c.isPrivate as c_isPrivate,
                     c.name as c_name,
                     c.photoPath as c_photoPath,
                     s.id as s_id,
                     s.name as s_name,
                     s.password as s_password,
                     s.login as s_login
                    from userInChat uc
                    inner join chats c on c.id = uc.chatId
                    inner join messages m on c.id = m.chatId
                    inner join users s on m.senderId = s.id
                    where uc.userId = :userId and
                            (m_senderId != :userId or (
                                m_senderId == :userId and
                                isDeletedForSender == 0
                            ))
                    order by m_dateSentMillis desc)
                inner join userInChat outerUs on outerUs.chatId = c_id
                inner join users d on d.id = outerUs.userId
                where d.id != :userId
                order by m_dateSentMillis desc)
            group by c_id  
            order by m_dateSentMillis desc
            """)
    fun getLastMessages(userId: Long)
        : Flow<List<LastMessageSelectResult>>


    @Query("""Select * from messages
            where chatId = :chatId and
                   (senderId != :currentUserId or (
                    senderId == :currentUserId and
                    isDeletedForSender == 0
                   ))""")
    fun getMessagesFromChatFlow(chatId: Long, currentUserId: Long): Flow<List<MessageEntity>>


    @Query("""Select * from messages
            where chatId = :chatId and
                   (senderId != :currentUserId or (
                    senderId == :currentUserId and
                    isDeletedForSender == 0
                   ))""")
    suspend fun getMessagesFromChat(chatId: Long, currentUserId: Long): List<MessageEntity>

    @Insert
    suspend fun insert(message: MessageEntity)

    @Update
    suspend fun update(message: MessageEntity)

    @Delete
    suspend fun delete(message: MessageEntity)

}