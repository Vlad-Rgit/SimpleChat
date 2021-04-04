package com.simplechat.android

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.simplechat.android.framework.data.LocalUserDataSource
import com.simplechat.android.framework.data.mappers.UserMapper
import com.simplechat.android.framework.database.SimpleChatDatabase
import com.simplechat.android.framework.utils.SharedPrefsHelper
import com.simplechat.core.domain.UserDomain
import com.simplechat.core.use_cases.login_screen.ErrorCredentialsException
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.jvm.Throws

@RunWith(AndroidJUnit4::class)
class LocalUserDataSourceTest {

    private lateinit var localUserDataSource: LocalUserDataSource
    private lateinit var database: SimpleChatDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider
            .getApplicationContext<Context>()

        database = SimpleChatDatabase.getInMemoryDatabase(context)

        localUserDataSource = LocalUserDataSource(
            database,
            SharedPrefsHelper(context),
            UserMapper()
        )
    }

    @After
    fun closeDb() {
        database.close()
    }


    @Test
    fun loginCorrectCredentials(): Unit = runBlocking {
        localUserDataSource.addUser(UserDomain(
            0,
            "User1",
            "User1",
            "User1"
        ))
        localUserDataSource.login("User1", "User1")
    }

    @Test
    @Throws(ErrorCredentialsException::class)
    fun loginWithErrorCredentials(): Unit = runBlocking {
        try {
            localUserDataSource.login("e", "e")
            assert(false) {
                "ErrorCredentialsException is not thrown"
            }
        }
        catch (ex: Exception) {
            assert(ex is ErrorCredentialsException)
        }
    }

}