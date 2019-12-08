package com.valentinvstoyanov.challenger.user.domain.repository

import com.valentinvstoyanov.challenger.user.domain.model.CreateUser
import com.valentinvstoyanov.challenger.user.domain.model.User
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface UserRepository {
    fun create(user: CreateUser): Mono<User>
    fun update(user: User): Mono<User>
    fun getByUsernameOrEmail(usernameOrEmail: String): Mono<User>
    fun getById(id: String): Mono<User>
    fun getAll(): Flux<User>
    fun delete(id: String): Mono<User>
}