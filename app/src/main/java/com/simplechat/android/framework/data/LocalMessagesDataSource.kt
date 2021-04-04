package com.simplechat.android.framework.data

import com.simplechat.android.framework.database.SimpleChatDatabase
import com.simplechat.android.framework.database.entities.LastMessageSelectResult
import com.simplechat.android.framework.database.entities.MessageEntity
import com.simplechat.core.data.MessagesDataSource
import com.simplechat.core.domain.*
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@ViewModelScoped
class LocalMessagesDataSource @Inject constructor(
    private val messageMapper: EntityMapper<MessageEntity, MessageDomain>,
    private val lastMessageMapper: EntityMapper<LastMessageSelectResult, LastMessageDomain>,
    private val database: SimpleChatDatabase
): MessagesDataSource {

    override fun getLastMessages(user: UserDomain): Flow<List<LastMessageDomain>> {
        return database.messagesDao.getLastMessages(user.id)
            .map {
                lastMessageMapper.toDomains(it)
            }
    }

    override fun getMessagesFlow(chatId: Long, currentUserId: Long): Flow<List<MessageDomain>> {
        return database.messagesDao.getMessagesFromChatFlow(chatId, currentUserId)
            .map {
                messageMapper.toDomains(it)
            }
    }

    override suspend fun getMessages(chatId: Long, currentUserId: Long): List<MessageDomain> {
        return database.messagesDao.getMessagesFromChat(chatId, currentUserId)
            .map {
                messageMapper.toDomain(it)
            }
    }


    override suspend fun deleteMessage(message: MessageDomain, isForAll: Boolean) {

        val entity = messageMapper.toEntity(message)

        if(isForAll) {
            database.messagesDao.delete(entity)
        }
        else {
            database.messagesDao.update(
                MessageEntity(
                    id = entity.id,
                    text = entity.text,
                    senderId = entity.senderId,
                    chatId = entity.chatId,
                    dateReadMillis = entity.dateReadMillis,
                    dateSentMillis = entity.dateSentMillis,
                    isDeletedForSender = true,
                )
            )
        }
    }

    override suspend fun sendMessage(message: MessageDomain) {
        val entity = messageMapper.toEntity(message)
        database.messagesDao.insert(entity)
    }

}