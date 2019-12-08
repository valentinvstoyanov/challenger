package com.valentinvstoyanov.challenger.user.domain.usecase

import com.valentinvstoyanov.challenger.user.domain.model.User
import com.valentinvstoyanov.challenger.user.domain.repository.UserRepository
import reactor.core.publisher.Flux

class GetAllUsersUseCase(private val userRepository: UserRepository) {
    operator fun invoke(): Flux<User> = userRepository.getAll()
}