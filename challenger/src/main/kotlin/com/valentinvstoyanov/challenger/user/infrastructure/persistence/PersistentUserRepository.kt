package com.valentinvstoyanov.challenger.user.infrastructure.persistence

import com.valentinvstoyanov.challenger.user.domain.model.CreateUser
import com.valentinvstoyanov.challenger.user.domain.model.User
import com.valentinvstoyanov.challenger.user.domain.repository.UserRepository
import com.valentinvstoyanov.challenger.user.infrastructure.persistence.dao.UserDao
import com.valentinvstoyanov.challenger.user.infrastructure.persistence.entity.DbUser
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

class PersistentUserRepository(private val userDao: UserDao) : UserRepository {
    override fun create(user: CreateUser): Mono<User> = userDao.insert(DbUser.from(user)).map { it.toUser() }

    override fun update(user: User): Mono<User> = userDao.save(DbUser.from(user)).map { it.toUser() }

    override fun getByUsernameOrEmail(usernameOrEmail: String): Mono<User> =
        userDao.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail).map { it.toUser() }

    override fun getById(id: String): Mono<User> = userDao.findById(id).map { it.toUser() }

    override fun getAll(): Flux<User> = userDao.findAll().map { it.toUser() }

    override fun delete(id: String): Mono<User> =
        userDao.findById(id).flatMap { userDao.delete(it).thenReturn(it) }.map { it.toUser() }
}