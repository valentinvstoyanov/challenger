package com.valentinvstoyanov.challenger.user.application

import com.valentinvstoyanov.challenger.ApiError
import com.valentinvstoyanov.challenger.ApiSubError
import com.valentinvstoyanov.challenger.user.domain.model.*
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.codec.HttpMessageWriter
import org.springframework.web.reactive.function.server.HandlerStrategies
import org.springframework.web.reactive.function.server.HandlerStrategies.withDefaults
import org.springframework.web.reactive.function.server.ServerResponse.Context
import org.springframework.web.reactive.function.server.ServerResponse.badRequest
import org.springframework.web.reactive.result.view.ViewResolver
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

class UserExceptionHandler : ErrorWebExceptionHandler {
    override fun handle(exchange: ServerWebExchange, ex: Throwable): Mono<Void> {
        if (ex !is UserException)
            return ex.toMono()

        return when (ex) {
            is UsernameAlreadyTakenException -> {
                badRequest()
                    .bodyValue(ApiError(BAD_REQUEST.value(),"Username already taken", listOf(ApiSubError("users", "There is user with this username", "${ex.username} already taken"))))
                    .flatMap { it.writeTo(exchange, HandlerStrategiesResponseContext(withDefaults())) }
            }
            is EmailAlreadyTakenException -> {
                badRequest()
                    .bodyValue(ApiError(BAD_REQUEST.value(), "Email already in use", listOf(ApiSubError("users", "There is user with this email", "${ex.email} already taken"))))
                    .flatMap { it.writeTo(exchange, HandlerStrategiesResponseContext(withDefaults())) }
            }
            is UserValidationException -> {
                badRequest()
                    .bodyValue(ApiError(BAD_REQUEST.value(), "User validation failed", listOf(ApiSubError("users", "User fields validation failed", ex.hint))))
                    .flatMap { it.writeTo(exchange, HandlerStrategiesResponseContext(withDefaults())) }
            }
            is UserNotFoundException -> {
                badRequest()
                    .bodyValue(ApiError(BAD_REQUEST.value(), "User not found", listOf(ApiSubError("users", "User was not found", "Failed to find user by ${ex.propertyName} given ${ex.propertyValue}"))))
                    .flatMap { it.writeTo(exchange, HandlerStrategiesResponseContext(withDefaults())) }
            }
        }
    }

    class HandlerStrategiesResponseContext(private val handlerStrategies: HandlerStrategies) : Context {
        override fun viewResolvers(): MutableList<ViewResolver> = handlerStrategies.viewResolvers()

        override fun messageWriters(): MutableList<HttpMessageWriter<*>> = handlerStrategies.messageWriters()
    }
}