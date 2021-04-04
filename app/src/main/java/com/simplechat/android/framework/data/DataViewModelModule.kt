package com.simplechat.android.framework.data

import com.simplechat.core.data.ChatDataSource
import com.simplechat.core.data.MessagesDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface DataViewModelModule {

    @Binds
    fun bindChatDataSource(
        impl: LocalChatDataSource
    ): ChatDataSource

    @Binds
    fun bindMessagesDataSource(
        impl: LocalMessagesDataSource
    ): MessagesDataSource
}