package com.simplechat.android

import android.content.Context
import com.simplechat.android.framework.database.DatabaseModule
import com.simplechat.android.framework.database.SimpleChatDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import org.junit.runner.manipulation.Ordering
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DatabaseModule::class]
)
class TestDatabaseModule {

    @Singleton
    @Provides
    fun provideSimpleChatDatabase(@ApplicationContext context: Context)
        : SimpleChatDatabase {
        return SimpleChatDatabase
            .getInMemoryDatabase(context)
    }

}