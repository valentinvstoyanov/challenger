package com.valentinvstoyanov.challenger.users.application.security

import com.valentinvstoyanov.challenger.users.domain.TokenService
import com.valentinvstoyanov.challenger.users.domain.UserService
import com.valentinvstoyanov.challenger.users.domain.model.Token
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import reactor.core.publisher.Mono

class AuthenticationManager(private val tokenService: TokenService, private val userService: UserService) : ReactiveAuthenticationManager {
    override fun authenticate(authentication: Authentication?): Mono<Authentication> {
        return Mono.justOrEmpty(authentication)
            .map { it.credentials.toString() }
            .flatMap { tokenService.parseToken(Token(it)) }
            .flatMap { userService.getUserById(it.userId) }
            .map { UsernamePasswordAuthenticationToken(it.id, it.password, emptyList()) }
    }
}