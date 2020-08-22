package com.valentinvstoyanov.challenger.users.application.handler

import com.valentinvstoyanov.challenger.ApiError
import com.valentinvstoyanov.challenger.ApiSubError
import com.valentinvstoyanov.challenger.HandlerStrategiesResponseContext
import com.valentinvstoyanov.challenger.users.domain.model.InvalidTokenException
import com.valentinvstoyanov.challenger.users.domain.model.TokenExpiredException
import com.valentinvstoyanov.challenger.users.domain.model.UserAuthenticationException
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler
import org.springframework.http.HttpStatus
import org.springframework.web.reactive.function.server.HandlerStrategies
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

class UserAuthenticationExceptionHandler : ErrorWebExceptionHandler {
    override fun handle(exchange: ServerWebExchange, ex: Throwable): Mono<Void> {
        if (ex !is UserAuthenticationException)
            return ex.toMono()

        val handled = when (ex) {
            is TokenExpiredException -> ServerResponse.status(HttpStatus.UNAUTHORIZED)
                .bodyValue(ApiError(HttpStatus.UNAUTHORIZED.value(), "User authentication failed", listOf(ApiSubError("users", "Token is expired", "Can't authenticate user with expired token"))))
            is InvalidTokenException -> ServerResponse.status(HttpStatus.UNAUTHORIZED)
                .bodyValue(ApiError(HttpStatus.UNAUTHORIZED.value(), "User authentication failed", listOf(ApiSubError("users", "Token is invalid", "Can't authenticate user with invalid token"))))
        }

        return handled.flatMap { it.writeTo(exchange, HandlerStrategiesResponseContext(HandlerStrategies.withDefaults())) }
    }
}