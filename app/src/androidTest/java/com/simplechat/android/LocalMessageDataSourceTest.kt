package com.simplechat.android

import com.simplechat.android.framework.data.LocalMessagesDataSource
import com.simplechat.android.framework.data.mappers.LastMessageMapper
import com.simplechat.android.framework.data.mappers.MessageMapper
import com.simplechat.android.framework.database.SimpleChatDatabase
import com.simplechat.android.framework.database.entities.ChatEntity
import com.simplechat.android.framework.database.entities.UserEntity
import com.simplechat.core.domain.MessageDomain
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.LocalDateTime
import javax.inject.Inject

@HiltAndroidTest
class LocalMessageDataSourceTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var database: SimpleChatDatabase

    lateinit var localMessagesDataSource: LocalMessagesDataSource

    @Before
    fun initDb() = runBlocking {

        hiltRule.inject()

        localMessagesDataSource = LocalMessagesDataSource(
            MessageMapper(),
            LastMessageMapper(),
            database
        )

        var users = listOf(UserEntity(
            0,
            "User1",
            "User1",
            "User1"
        ),
            UserEntity(
                0,
                "User2",
                "User2",
                "User2"
            )
        )

        users = listOf(UserEntity(
            database.userDao.insert(users[0]),
            "User1",
            "User1",
            "User1"
        ),
            UserEntity(
                database.userDao.insert(users[1]),
                "User2",
                "User2",
                "User2"
            )
        )

        database.chatDao.insertAndAddUsers(
            ChatEntity(
                0,
                "test chat",
                true,
                null
            ),
            users
        )
    }

    @Test
    fun writeAndReadMessage() = runBlocking {

        val text = "Test"

        localMessagesDataSource.sendMessage(MessageDomain(
            0,
            1,
            1,
            text,
            LocalDateTime.now(),
            LocalDateTime.now(),
            false
        ))

        val messages = localMessagesDataSource.getMessagesFlow(
            1,
            1
        ).first()

        assert(messages[0].text == text)
    }

    @Test
    fun markAsDeleteForSender() = runBlocking {

        localMessagesDataSource.sendMessage(MessageDomain(
            0,
            1,
            1,
            "Test",
            LocalDateTime.now(),
            LocalDateTime.now(),
            false
        ))

        val sentMessage = localMessagesDataSource
            .getMessages(1, 1)
            .last()

        localMessagesDataSource.deleteMessage(
            sentMessage,
            false
        )


        val userMessages = localMessagesDataSource
            .getMessages(1, 1)

        assert(userMessages.isEmpty())
    }


    @Test
    fun markAsDeleteForSenderCheckAsOtherUser() = runBlocking {

        localMessagesDataSource.sendMessage(MessageDomain(
            0,
            1,
            1,
            "Test",
            LocalDateTime.now(),
            LocalDateTime.now(),
            false
        ))

        val sentMessage = localMessagesDataSource
            .getMessages(1, 1)
            .last()

        localMessagesDataSource.deleteMessage(
            sentMessage,
            false
        )


        val userMessages = localMessagesDataSource
            .getMessages(1, 2)

        assert(userMessages[0].isDeletedForSender)
    }

}