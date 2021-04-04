package com.simplechat.android.framework.data.mappers

import com.simplechat.android.framework.database.entities.*
import com.simplechat.core.domain.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface MappersModule {

    @Binds
    fun bindUserMapper(
        impl: UserMapper
    ): EntityMapper<UserEntity, UserDomain>

    @Binds
    fun bindChatMapper(
        impl: ChatMapper
    ): EntityMapper<ChatEntity, ChatDomain>

    @Binds
    fun bindMessageMapper(
        impl: MessageMapper
    ): EntityMapper<MessageEntity, MessageDomain>

    @Binds
    fun bindLastMessageMapper(
        impl: LastMessageMapper
    ): EntityMapper<LastMessageSelectResult, LastMessageDomain>

    @Binds
    fun bindChatInfoMapper(
        impl: ChatInfoMapper
    ): EntityMapper<ChatInfoSelectResult, ChatInfoDomain>

    @Binds
    fun bindSendingMessageMapper(
        impl: SendingMessageMapper
    ): EntityMapper<MessageDomain, SendingMessage>


}