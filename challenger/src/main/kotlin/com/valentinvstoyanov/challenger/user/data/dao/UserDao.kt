package com.valentinvstoyanov.challenger.user.data.dao

import com.valentinvstoyanov.challenger.user.data.entity.DbUser
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Mono

interface UserDao : ReactiveMongoRepository<DbUser, String> {
    fun getByUsername(username: String): Mono<DbUser>
    fun getByEmail(email: String): Mono<DbUser>
}