package com.valentinvstoyanov.challenger.user.application.security

import com.valentinvstoyanov.challenger.user.domain.TokenService
import com.valentinvstoyanov.challenger.user.domain.UserService
import com.valentinvstoyanov.challenger.user.domain.model.Token
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import reactor.core.publisher.Mono

class AuthenticationManager(private val tokenService: TokenService, private val userService: UserService) : ReactiveAuthenticationManager {
    override fun authenticate(authentication: Authentication?): Mono<Authentication> {
        return Mono.justOrEmpty(authentication)
            .map { it.credentials.toString() }
            .flatMap { tokenService.parseToken(Token(it)) }
            .flatMap { userService.getUserById(it.userId) }
            .map { UsernamePasswordAuthenticationToken(it.username, it.password, listOf(SimpleGrantedAuthority("ROLE_USER"))) }
    }
}