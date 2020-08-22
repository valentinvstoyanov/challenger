package com.valentinvstoyanov.challenger.users.application.security

import com.valentinvstoyanov.challenger.users.domain.model.Token.Companion.BEARER
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextImpl
import org.springframework.security.web.server.context.ServerSecurityContextRepository
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

class SecurityContextRepository(private val authenticationManager: ReactiveAuthenticationManager) : ServerSecurityContextRepository {
    override fun save(exchange: ServerWebExchange?, context: SecurityContext?): Mono<Void> {
        throw UnsupportedOperationException()
    }

    override fun load(exchange: ServerWebExchange?): Mono<SecurityContext> {
        val authHeader = exchange?.request?.headers?.getFirst(HttpHeaders.AUTHORIZATION)
        val token = authHeader?.takeIf { it.startsWith(BEARER) }

        return Mono.justOrEmpty(token)
            .map { UsernamePasswordAuthenticationToken(it, it) }
            .flatMap { authenticationManager.authenticate(it) }
            .map { SecurityContextImpl(it) }
    }
}