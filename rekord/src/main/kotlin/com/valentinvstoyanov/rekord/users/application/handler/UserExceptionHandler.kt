package com.valentinvstoyanov.rekord.users.application.handler

import com.valentinvstoyanov.rekord.ApiError
import com.valentinvstoyanov.rekord.ApiSubError
import com.valentinvstoyanov.rekord.HandlerStrategiesResponseContext
import com.valentinvstoyanov.rekord.users.domain.model.*
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.HttpStatus.UNAUTHORIZED
import org.springframework.web.reactive.function.server.HandlerStrategies.withDefaults
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.badRequest
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

class UserExceptionHandler : ErrorWebExceptionHandler {
    override fun handle(exchange: ServerWebExchange, ex: Throwable): Mono<Void> {
        if (ex !is UserException)
            return ex.toMono()

        val handled = when (ex) {
            is UsernameAlreadyTakenException -> {
                badRequest()
                    .bodyValue(ApiError(BAD_REQUEST.value(),"Username already taken", listOf(ApiSubError("users", "There is user with this username", "${ex.username} already taken"))))
            }
            is EmailAlreadyTakenException -> {
                badRequest()
                    .bodyValue(ApiError(BAD_REQUEST.value(), "Email already in use", listOf(ApiSubError("users", "There is user with this email", "${ex.email} already taken"))))
            }
            is UserValidationException -> {
                badRequest()
                    .bodyValue(ApiError(BAD_REQUEST.value(), "User validation failed", listOf(ApiSubError("users", "User fields validation failed", ex.hint))))
            }
            is UserNotFoundException -> {
                badRequest()
                    .bodyValue(ApiError(BAD_REQUEST.value(), "User not found", listOf(ApiSubError("users", "User was not found", "Failed to find user by ${ex.propertyName} given ${ex.propertyValue}"))))
            }
            is UserLoginException -> {
                ServerResponse.status(UNAUTHORIZED)
                    .bodyValue(ApiError(UNAUTHORIZED.value(), "User login failed", listOf(ApiSubError("users", "User credentials error", "Failed to login user with ${ex.emailOrUsername}"))))
            }
            is UserPasswordUpdateException -> {
                badRequest()
                    .bodyValue(ApiError(BAD_REQUEST.value(), "Password update failed", listOf(ApiSubError("users", ex.hint, "Failed to change user password"))))
            }
        }

        return handled.flatMap { it.writeTo(exchange, HandlerStrategiesResponseContext(withDefaults())) }
    }
}

