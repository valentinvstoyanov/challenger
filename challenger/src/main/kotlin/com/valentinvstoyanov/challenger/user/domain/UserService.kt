package com.valentinvstoyanov.challenger.user.domain

import com.valentinvstoyanov.challenger.user.domain.model.CreateUser
import com.valentinvstoyanov.challenger.user.domain.model.EmailAlreadyTakenException
import com.valentinvstoyanov.challenger.user.domain.model.User
import com.valentinvstoyanov.challenger.user.domain.model.UsernameAlreadyTakenException
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty

interface UserService {
    fun createUser(user: CreateUser): Mono<User>
}

class UserServiceImpl(private val userRepository: UserRepository, private val userValidator: UserValidator) : UserService {
    override fun createUser(user: CreateUser): Mono<User> {
        return userValidator.validate(user)
            .filterWhen { userRepository.getByUsername(user.username).hasElement().map { !it } }
            .switchIfEmpty { Mono.error(UsernameAlreadyTakenException) }
            .filterWhen { userRepository.getByEmail(user.email).hasElement().map { !it } }
            .switchIfEmpty { Mono.error(EmailAlreadyTakenException) }
            .flatMap { userRepository.create(it) }
    }
}

