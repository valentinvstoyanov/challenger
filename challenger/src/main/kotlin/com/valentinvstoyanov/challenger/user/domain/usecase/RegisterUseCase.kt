package com.valentinvstoyanov.challenger.user.domain.usecase

import com.valentinvstoyanov.challenger.user.domain.model.CreateUser
import com.valentinvstoyanov.challenger.user.domain.model.User
import com.valentinvstoyanov.challenger.user.domain.repository.UserRepository
import reactor.core.publisher.Mono

class RegisterUseCase(private val userRepository: UserRepository) {
    operator fun invoke(user: CreateUser): Mono<User> = userRepository.create(user)
}
