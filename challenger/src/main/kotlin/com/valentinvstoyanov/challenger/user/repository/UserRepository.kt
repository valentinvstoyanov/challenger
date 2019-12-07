package com.valentinvstoyanov.challenger.user.repository

import com.valentinvstoyanov.challenger.user.model.User
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface UserRepository {
    fun create(user: User): Mono<User>
    fun update(user: User): Mono<User>
    fun getByUsername(username: String): Mono<User>
    fun getByEmail(email: String): Mono<User>
    fun getAll(): Flux<User>
    fun delete(user: User): Mono<User>
}