package com.valentinvstoyanov.rekord.users.domain

import com.valentinvstoyanov.rekord.users.domain.model.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty
import reactor.kotlin.core.publisher.toMono
import reactor.kotlin.core.util.function.component1
import reactor.kotlin.core.util.function.component2

interface UserService {
    fun createUser(user: CreateUser): Mono<User>
    fun updateUser(id: String, user: UpdateUser): Mono<User>
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

    override fun updateUser(id: String, user: UpdateUser): Mono<User> =
        if (!user.hasChanges) {
            getUserById(id)
        } else {
            userValidator.validate(user)
                .filter { it.newPassword == null || it.oldPassword != null }
                .switchIfEmpty { UserPasswordUpdateException("Оld password missing.").toMono() }
                .zipWith(getUserById(id))
                .filter { (validatedUser, oldUser) ->
                    validatedUser.oldPassword == null ||
                            passwordEncoder.match(raw = validatedUser.oldPassword, encoded = oldUser.password)
                }
                .switchIfEmpty { UserPasswordUpdateException("Old password does not match.").toMono() }
                .filterWhen { (validatedUser, oldUser) ->
                    when (validatedUser.username) {
                        null, oldUser.username -> true.toMono()
                        else -> userRepository.getByUsername(validatedUser.username).hasElement().map { !it }
                    }
                }
                .switchIfEmpty { UsernameAlreadyTakenException(user.username!!).toMono() }
                .filterWhen { (validatedUser, oldUser) ->
                    when (validatedUser.email) {
                        null, oldUser.email -> true.toMono()
                        else -> userRepository.getByEmail(validatedUser.email).hasElement().map { !it }
                    }
                }
                .switchIfEmpty { EmailAlreadyTakenException(user.email!!).toMono() }
                .map { (validatedUser, oldUser) ->
                    oldUser.copy(
                        name = validatedUser.name ?: oldUser.name,
                        email = validatedUser.email ?: oldUser.email,
                        username = validatedUser.username ?: oldUser.username,
                        password = if (validatedUser.newPassword != null) passwordEncoder(validatedUser.newPassword) else oldUser.password//validatedUser.newPassword?.let { passwordEncoder(it) } ?: oldUser.password
                    )
                }
                .flatMap { userRepository.update(it) }
        }

    override fun loginUser(user: LoginUser): Mono<User> =
        userRepository.getByEmail(user.emailOrUsername)
            .switchIfEmpty { userRepository.getByUsername(user.emailOrUsername) }
            .flatMap { if (passwordEncoder.match(raw = user.password, encoded = it.password)) it.toMono() else Mono.empty() }
            .switchIfEmpty { UserLoginException(user.emailOrUsername).toMono() }

    override fun getUserById(id: String): Mono<User> =
        userRepository.getById(id).switchIfEmpty { UserNotFoundException("id", id).toMono() }

    override fun getAllUsers(): Flux<User> = userRepository.getAll()
}

