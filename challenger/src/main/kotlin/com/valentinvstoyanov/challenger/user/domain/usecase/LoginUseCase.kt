package com.valentinvstoyanov.challenger.user.domain.usecase

import com.valentinvstoyanov.challenger.user.domain.model.User
import com.valentinvstoyanov.challenger.user.domain.repository.UserRepository
import reactor.core.publisher.Mono

class LoginUseCase(private val userRepository: UserRepository) {
    operator fun invoke(emailOrUsername: String, password: String): Mono<User> =
        userRepository.getByUsernameOrEmail(emailOrUsername).filter { it.password == password }
}