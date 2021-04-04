package com.simplechat.android.framework.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.simplechat.android.framework.database.dao.ChatDao
import com.simplechat.android.framework.database.dao.MessagesDao
import com.simplechat.android.framework.database.dao.UserDao
import com.simplechat.android.framework.database.entities.ChatEntity
import com.simplechat.android.framework.database.entities.MessageEntity
import com.simplechat.android.framework.database.entities.UserChatCrossRefEntity
import com.simplechat.android.framework.database.entities.UserEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.time.Instant


@Database(
    entities = [
        MessageEntity::class,
        ChatEntity::class,
        UserEntity::class,
        UserChatCrossRefEntity::class
    ],
    version = 4,
    exportSchema = false
)
abstract class SimpleChatDatabase: RoomDatabase() {

    abstract val userDao: UserDao
    abstract val chatDao: ChatDao
    abstract val messagesDao: MessagesDao

    companion object {

        val databaseName = "simpleChatDB"

        private var _instance: SimpleChatDatabase? = null

        val prepopulateRoomCallback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)

                val instance = _instance!!
                val userDao = _instance!!.userDao

                //Run blocking to ensure that data is initialized before use
                CoroutineScope(Dispatchers.IO).launch {
                    initUsers(userDao)
                    initChats(userDao, instance.chatDao)
                    initMessages(
                        instance.userDao,
                        instance.chatDao,
                        instance.messagesDao
                    )
                }
            }

            private suspend fun initMessages(
                userDao: UserDao,
                chatDao: ChatDao,
                messagesDao: MessagesDao
            ) {

                val chats = chatDao.getAll()
                val users = userDao.getAll()

                messagesDao.insert(
                    MessageEntity(
                       chatId = chats[0].id,
                       senderId = users[0].id,
                       text = "Test message1",
                       dateSentMillis = Instant.now().toEpochMilli()
                    )
                )

                messagesDao.insert(
                    MessageEntity(
                        chatId = chats[0].id,
                        senderId = users[1].id,
                        text = "Test message2",
                        dateSentMillis = Instant.now().toEpochMilli()
                    )
                )

                messagesDao.insert(
                    MessageEntity(
                        chatId = chats[1].id,
                        senderId = users[0].id,
                        text = "Test message12",
                        dateSentMillis = Instant.now().toEpochMilli()
                    )
                )
            }

            private suspend fun initChats(userDao: UserDao, chatDao: ChatDao) {

                val users = userDao.getAll()

                chatDao.insertAndAddUsers(
                    ChatEntity(
                        name = "private chat 1 2",
                        isPrivate = true
                    ),
                    listOf(users[0], users[1])
                )

                chatDao.insertAndAddUsers(
                    ChatEntity(
                        name = "private chat 1 3",
                        isPrivate = true
                    ),
                    listOf(users[0], users[2])
                )
            }

            private suspend fun initUsers(userDao: UserDao) {
                userDao.insert(
                    UserEntity(
                        name = "User1",
                        password = "User1",
                        login = "User1"
                    ))

                userDao.insert(
                    UserEntity(
                        name = "User2",
                        password = "User2",
                        login = "User2"
                    ))

                userDao.insert(
                    UserEntity(
                        name = "User3",
                        password = "User3",
                        login = "User3"
                    ))
            }
        }

        @Synchronized
        fun getInMemoryDatabase(context: Context): SimpleChatDatabase {
            var instance = _instance

            if(instance == null) {
                instance = Room.inMemoryDatabaseBuilder(
                    context,
                    SimpleChatDatabase::class.java
                ).build()


                //Trigger onCreate callback
                CoroutineScope(Dispatchers.IO).launch {
                    instance.userDao.count()
                }

                _instance = instance
            }

            return instance
        }

        @Synchronized
        fun getInstance(context: Context): SimpleChatDatabase {

            var instance = _instance

            if(instance == null) {
                instance = Room.databaseBuilder(
                    context,
                    SimpleChatDatabase::class.java,
                    databaseName
                ).fallbackToDestructiveMigration()
                    .addCallback(prepopulateRoomCallback)
                    .build()

                //Trigger onCreate callback
                CoroutineScope(Dispatchers.IO).launch {
                    instance.userDao.count()
                }

                _instance = instance
            }

            return instance
        }
    }

}