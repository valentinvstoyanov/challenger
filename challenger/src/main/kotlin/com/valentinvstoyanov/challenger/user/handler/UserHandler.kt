package com.valentinvstoyanov.challenger.user.handler

import com.valentinvstoyanov.challenger.user.domain.model.CreateUser
import com.valentinvstoyanov.challenger.user.domain.model.User
import com.valentinvstoyanov.challenger.user.domain.usecase.*
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.created
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.bodyToMono
import org.springframework.web.reactive.function.server.json
import reactor.core.publisher.Mono
import java.net.URI

class UserHandler(
    private val registerUser: RegisterUseCase,
    private val updateUser: UpdateUserUseCase,
    private val deleteUser: DeleteUserUseCase,
    private val getUserById: GetUserByIdUseCase,
    private val getAllUsers: GetAllUsersUseCase
) {
    fun createUser(request: ServerRequest): Mono<ServerResponse> =
        request.bodyToMono<CreateUser>()
            .flatMap { registerUser(it) }
            .flatMap { created(URI.create("${request.path()}/${it.id}")).json().bodyValue(it) }

    fun updateUser(request: ServerRequest): Mono<ServerResponse> =
        request.bodyToMono<User>()
            .filter { it.id == request.pathVariable("id") }
            .flatMap { updateUser(it) }
            .flatMap { ok().json().bodyValue(it) }

    fun deleteUser(request: ServerRequest): Mono<ServerResponse> =
        deleteUser(request.pathVariable("id")).flatMap { ok().json().bodyValue(it) }

    fun getUserById(request: ServerRequest): Mono<ServerResponse> =
        getUserById(request.pathVariable("id")).flatMap { ok().json().bodyValue(it) }

    fun getAllUsers(request: ServerRequest): Mono<ServerResponse> =
        ok().json().bodyValue(getAllUsers())
}