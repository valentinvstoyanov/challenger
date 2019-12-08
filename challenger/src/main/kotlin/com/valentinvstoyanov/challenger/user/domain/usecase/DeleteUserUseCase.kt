package com.valentinvstoyanov.challenger.user.domain.usecase

import com.valentinvstoyanov.challenger.user.domain.model.User
import com.valentinvstoyanov.challenger.user.domain.repository.UserRepository
import reactor.core.publisher.Mono

class DeleteUserUseCase(private val userRepository: UserRepository) {
    operator fun invoke(id: String): Mono<User> = userRepository.delete(id)
}