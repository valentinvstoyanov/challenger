package com.valentinvstoyanov.challenger.users.application.security

import org.springframework.http.HttpMethod
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.context.ServerSecurityContextRepository

@EnableWebFluxSecurity
class SecurityConfig(
    private val authenticationManager: ReactiveAuthenticationManager,
    private val serverSecurityContextRepository: ServerSecurityContextRepository
) {
    fun springSecurityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain =
        http.csrf().disable()
            .formLogin().disable()
            .httpBasic().disable()
            .authenticationManager(authenticationManager)
            .securityContextRepository(serverSecurityContextRepository)
            .authorizeExchange()
            .pathMatchers(HttpMethod.POST, "/api/users/login", "/api/users").permitAll()
            .pathMatchers(HttpMethod.GET, "/actuator/**").permitAll()
            .anyExchange().authenticated()
            .and().build()
}