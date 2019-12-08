package com.valentinvstoyanov.challenger.user.infrastructure.persistence.dao

import com.valentinvstoyanov.challenger.user.infrastructure.persistence.entity.DbUser
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Mono

interface UserDao : ReactiveMongoRepository<DbUser, String> {
    fun findByUsernameOrEmail(username: String, email: String): Mono<DbUser>
}