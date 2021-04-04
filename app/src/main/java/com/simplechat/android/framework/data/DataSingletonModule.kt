package com.simplechat.android.framework.data

import com.simplechat.core.data.UserDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataSingletonModule {

    @Binds
    fun bindUserDataSource(
      impl: LocalUserDataSource
    ): UserDataSource

}