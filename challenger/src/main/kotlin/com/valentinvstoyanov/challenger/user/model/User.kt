package com.valentinvstoyanov.challenger.user.model

import java.time.Instant

data class User(
    val id: String,
    val name: String,
    val email: String,
    val username: String,
    val password: String,
    val followers: List<User>,
    val following: List<User>,
    val createdAt: Instant
)