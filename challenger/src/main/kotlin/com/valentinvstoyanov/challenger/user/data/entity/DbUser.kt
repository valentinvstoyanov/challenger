package com.valentinvstoyanov.challenger.user.data.entity

import com.valentinvstoyanov.challenger.user.domain.model.CreateUser
import com.valentinvstoyanov.challenger.user.domain.model.User
import org.springframework.data.annotation.Id
import java.time.Instant

data class DbUser(
    @Id val id: String?,
    val name: String,
    val email: String,
    val username: String,
    val password: String,
    val createdAt: Instant
) {
    companion object {
        fun from(user: User): DbUser =
            DbUser(
                id = user.id,
                name = user.name,
                email = user.email,
                username = user.username,
                password = user.password,
                createdAt = user.createdAt
            )

        fun from(user: CreateUser): DbUser =
            DbUser(
                id = null,
                name = user.name,
                email = user.email,
                username = user.username,
                password = user.password,
                createdAt = Instant.now()
            )
    }

    fun toUser(): User = User(
        id = id!!,
        name = name,
        email = email,
        username = username,
        password = password,
        createdAt = createdAt
    )
}