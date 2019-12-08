package com.valentinvstoyanov.challenger.user.domain.usecase

import com.valentinvstoyanov.challenger.user.domain.model.User
import com.valentinvstoyanov.challenger.user.domain.repository.UserRepository
import reactor.core.publisher.Mono

class UpdateUserUseCase(private val userRepository: UserRepository) {
    operator fun invoke(user: User): Mono<User> = userRepository.update(user)
}