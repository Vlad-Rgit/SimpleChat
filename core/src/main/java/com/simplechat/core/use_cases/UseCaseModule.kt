package com.simplechat.core.use_cases

import com.simplechat.core.use_cases.chat_screen.*
import com.simplechat.core.use_cases.home_screen.ClearCachedUser
import com.simplechat.core.use_cases.home_screen.ClearCachedUserImpl
import com.simplechat.core.use_cases.home_screen.GetLastMessages
import com.simplechat.core.use_cases.home_screen.GetLastMessagesImpl
import com.simplechat.core.use_cases.login_screen.LoginUser
import com.simplechat.core.use_cases.login_screen.LoginUserImpl
import com.simplechat.core.use_cases.splash_screen.LoginCachedUser
import com.simplechat.core.use_cases.splash_screen.LoginCachedUserImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface UseCaseModule {

    @Binds
    fun bindLoginUseCase(
        impl: LoginUserImpl
    ): LoginUser


    @Binds
    fun bindGetCurrentUserUseCase(
        impl: GetCurrentUserImpl
    ): GetCurrentUser

    @Binds
    fun bindLoginCachedUser(
        impl: LoginCachedUserImpl
    ): LoginCachedUser

    @Binds
    fun bindGetLastMessages(
        impl: GetLastMessagesImpl
    ): GetLastMessages

    @Binds
    fun bindGetMessages(
        impl: GetMessagesImpl
    ): GetMessages

    @Binds
    fun bindGetInfo(
        impl: GetChatInfoImpl
    ): GetChatInfo

    @Binds
    fun bindSendMessage(
        impl: SendMessageImpl
    ): SendMessage

    @Binds
    fun bindDeleteMessage(
        impl: DeleteMessageImpl
    ): DeleteMessage

    @Binds
    fun bindClearCachedUser(
        impl: ClearCachedUserImpl
    ): ClearCachedUser
}