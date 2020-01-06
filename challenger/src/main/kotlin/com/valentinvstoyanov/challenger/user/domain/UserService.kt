package com.valentinvstoyanov.challenger.user.domain

import com.valentinvstoyanov.challenger.user.domain.model.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty
import reactor.kotlin.core.publisher.toMono

interface UserService {
    fun createUser(user: CreateUser): Mono<User>
    fun updateUser(user: User): Mono<User>
    fun loginUser(user: LoginUser): Mono<User>
    fun getUserById(id: String): Mono<User>
    fun getAllUsers(): Flux<User>
}

class UserServiceImpl(private val userRepository: UserRepository, private val userValidator: UserValidator, private val passwordEncoder: PasswordEncoder) : UserService {
    override fun createUser(user: CreateUser): Mono<User> =
        userValidator.validate(user)
            .filterWhen { userRepository.getByUsername(user.username).hasElement().map { !it } }
            .switchIfEmpty { UsernameAlreadyTakenException(user.username).toMono() }
            .filterWhen { userRepository.getByEmail(user.email).hasElement().map { !it } }
            .switchIfEmpty { EmailAlreadyTakenException(user.email).toMono() }
            .flatMap { userRepository.create(it.copy(password = passwordEncoder(it.password))) }

    override fun updateUser(user: User): Mono<User> =
        userValidator.validate(user)
            .filterWhen { userRepository.getByEmail(user.email).filter { it.id == user.id }.map { true } }
            .switchIfEmpty { EmailAlreadyTakenException(user.email).toMono() }
            .filterWhen { userRepository.getByUsername(user.username).filter { it.id == user.id }.map { true } }
            .switchIfEmpty { UsernameAlreadyTakenException(user.username).toMono() }
            .flatMap { userRepository.update(it) }

    override fun loginUser(user: LoginUser): Mono<User> =
        userRepository.getByEmail(user.emailOrUsername)
            .switchIfEmpty { userRepository.getByUsername(user.emailOrUsername) }
            .flatMap { if (passwordEncoder.match(raw = user.password, encoded = it.password)) it.toMono() else Mono.empty() }
            .switchIfEmpty { UserLoginException(user.emailOrUsername).toMono() }

    override fun getUserById(id: String): Mono<User> =
        userRepository.getById(id).switchIfEmpty { UserNotFoundException("id", id).toMono() }

    override fun getAllUsers(): Flux<User> = userRepository.getAll()
}

