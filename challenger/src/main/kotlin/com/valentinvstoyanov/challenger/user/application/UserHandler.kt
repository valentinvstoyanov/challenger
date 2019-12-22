package com.valentinvstoyanov.challenger.user.application

import com.valentinvstoyanov.challenger.user.domain.UserService
import com.valentinvstoyanov.challenger.user.domain.model.CreateUser
import org.springframework.web.reactive.function.server.*
import org.springframework.web.reactive.function.server.ServerResponse.created
import org.springframework.web.reactive.function.server.ServerResponse.ok
import reactor.core.publisher.Mono
import java.net.URI

class UserHandler(private val userService: UserService) {
    fun createUser(request: ServerRequest): Mono<ServerResponse> =
        request.bodyToMono<CreateUser>()
            .flatMap { userService.createUser(it) }
            .flatMap { created(URI.create("${request.path()}/${it.id}")).json().bodyValue(it) }

//    fun updateUser(request: ServerRequest): Mono<ServerResponse> =
//        request.bodyToMono<User>()
//            .filter { it.id == request.pathVariable("id") }
//            .flatMap { updateUser(it) }
//            .flatMap { ok().json().bodyValue(it) }
//
//    fun deleteUser(request: ServerRequest): Mono<ServerResponse> =
//        deleteUser(request.pathVariable("id")).flatMap { ok().json().bodyValue(it) }
//
    fun getUserById(request: ServerRequest): Mono<ServerResponse> =
        userService.getUserById(request.pathVariable("id")).flatMap { ok().bodyValue(it) }

    fun getAllUsers(request: ServerRequest): Mono<ServerResponse> =
        ok().body(userService.getAllUsers())
}