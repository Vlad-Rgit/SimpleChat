package com.simplechat.core.domain

data class UserDomain(
    val id: Long,
    val name: String,
    val login: String,
    val password: String
)
