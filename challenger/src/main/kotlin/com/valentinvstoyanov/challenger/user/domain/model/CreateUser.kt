package com.valentinvstoyanov.challenger.user.domain.model

data class CreateUser(
    val name: String,
    val email: String,
    val username: String,
    val password: String
)