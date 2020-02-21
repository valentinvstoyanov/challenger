package com.valentinvstoyanov.challenger.user.application.handler

import com.valentinvstoyanov.challenger.user.domain.TokenService
import com.valentinvstoyanov.challenger.user.domain.UserService
import com.valentinvstoyanov.challenger.user.domain.model.CreateUser
import com.valentinvstoyanov.challenger.user.domain.model.LoginUser
import com.valentinvstoyanov.challenger.user.domain.model.UpdateUser
import org.springframework.web.reactive.function.server.*
import org.springframework.web.reactive.function.server.ServerResponse.created
import org.springframework.web.reactive.function.server.ServerResponse.ok
import reactor.core.publisher.Mono
import java.net.URI

class UserHandler(private val userService: UserService, private val tokenService: TokenService) {
    fun createUser(request: ServerRequest): Mono<ServerResponse> =
        request.bodyToMono<CreateUser>()
            .flatMap { userService.createUser(it) }
            .flatMap { created(URI.create("${request.path()}/${it.id}")).json().bodyValue(it) }

    fun updateUser(request: ServerRequest): Mono<ServerResponse> =
        request.bodyToMono<UpdateUser>()
            .flatMap { userService.updateUser(request.pathVariable("id"), it) }
            .flatMap { ok().json().bodyValue(it) }

    fun loginUser(request: ServerRequest): Mono<ServerResponse> =
        request.bodyToMono<LoginUser>()
            .flatMap { userService.loginUser(it) }
            .map { tokenService.createToken(it) }
            .flatMap { ok().bodyValue(it) }

//    fun deleteUser(request: ServerRequest): Mono<ServerResponse> =
//        deleteUser(request.pathVariable("id")).flatMap { ok().json().bodyValue(it) }
//

    fun getUserById(request: ServerRequest): Mono<ServerResponse> =
        userService.getUserById(request.pathVariable("id")).flatMap { ok().bodyValue(it) }

    fun getAllUsers(request: ServerRequest): Mono<ServerResponse> =
        ok().body(userService.getAllUsers())
}