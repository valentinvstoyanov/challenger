package com.valentinvstoyanov.challenger.challenges.application.handler

import com.valentinvstoyanov.challenger.ApiError
import com.valentinvstoyanov.challenger.ApiSubError
import com.valentinvstoyanov.challenger.challenges.domain.model.ChallengeException
import com.valentinvstoyanov.challenger.challenges.domain.model.ChallengeValidationException
import com.valentinvstoyanov.challenger.HandlerStrategiesResponseContext
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler
import org.springframework.http.HttpStatus
import org.springframework.web.reactive.function.server.HandlerStrategies
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

class ChallengeExceptionHandler : ErrorWebExceptionHandler {
    override fun handle(exchange: ServerWebExchange, ex: Throwable): Mono<Void> {
        if (ex !is ChallengeException)
            return ex.toMono()

        val handled = when(ex) {
            is ChallengeValidationException -> ServerResponse.badRequest()
                .bodyValue(ApiError(HttpStatus.BAD_REQUEST.value(), "Challenge validation failed", listOf(ApiSubError("challenges", "Challenge fields validation failed", ex.hint))))
        }

        return handled.flatMap { it.writeTo(exchange, HandlerStrategiesResponseContext(HandlerStrategies.withDefaults())) }
    }
}