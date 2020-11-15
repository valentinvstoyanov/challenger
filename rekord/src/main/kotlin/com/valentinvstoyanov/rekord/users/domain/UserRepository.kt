package com.valentinvstoyanov.rekord.users.domain

import com.valentinvstoyanov.rekord.users.domain.model.CreateUser
import com.valentinvstoyanov.rekord.users.domain.model.User
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface UserRepository {
    fun create(user: CreateUser): Mono<User>
    fun update(user: User): Mono<User>
    fun delete(id: String): Mono<User>

    fun getByEmail(email: String): Mono<User>
    fun getByUsername(username: String): Mono<User>
    fun getById(id: String): Mono<User>
    fun getAll(): Flux<User>
}