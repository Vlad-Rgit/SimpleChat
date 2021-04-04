package com.simplechat.android.framework.data.mappers

import com.simplechat.android.framework.database.entities.UserEntity
import com.simplechat.core.domain.EntityMapper
import com.simplechat.core.domain.UserDomain
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserMapper @Inject constructor()
    : EntityMapper<UserEntity, UserDomain> {

    override fun toEntity(e: UserDomain): UserEntity {
        return UserEntity(
            id = e.id,
            login = e.login,
            name = e.name,
            password = e.password
        )
    }

    override fun toDomain(e: UserEntity): UserDomain {
        return UserDomain(
            id = e.id,
            login = e.login,
            name = e.name,
            password = e.password
        )
    }

}