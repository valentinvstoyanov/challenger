package com.valentinvstoyanov.challenger.user.domain

import com.valentinvstoyanov.challenger.user.domain.model.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty
import reactor.kotlin.core.publisher.toMono

interface UserService {
    fun createUser(user: CreateUser): Mono<User>
    fun getUserById(id: String): Mono<User>
    fun getAllUsers(): Flux<User>
}

class UserServiceImpl(private val userRepository: UserRepository, private val userValidator: UserValidator) :
    UserService {
    override fun createUser(user: CreateUser): Mono<User> {
        return userValidator.validate(user)
            .filterWhen { userRepository.getByUsername(user.username).hasElement().map { !it } }
            .switchIfEmpty { UsernameAlreadyTakenException(user.username).toMono() }
            .filterWhen { userRepository.getByEmail(user.email).hasElement().map { !it } }
            .switchIfEmpty { EmailAlreadyTakenException(user.email).toMono() }
            .flatMap { userRepository.create(it) }
    }

    override fun getUserById(id: String): Mono<User> =
        userRepository.getById(id).switchIfEmpty { UserNotFoundException("id", id).toMono() }

    override fun getAllUsers(): Flux<User> = userRepository.getAll()
}

