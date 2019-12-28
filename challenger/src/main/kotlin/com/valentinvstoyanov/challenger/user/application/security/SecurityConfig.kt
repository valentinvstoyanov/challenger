package com.valentinvstoyanov.challenger.user.application.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService
import org.springframework.security.core.userdetails.User
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.context.ServerSecurityContextRepository

@Configuration
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
            .anyExchange().authenticated()
            .and().build()

    //TODO
    @Bean
    fun userDetailsRepository(): MapReactiveUserDetailsService {
        val enc = BCryptPasswordEncoder()
        val user = User.withUsername ("valio").password(enc.encode("valio123")).roles("USER").build()
        return MapReactiveUserDetailsService(user)
    }
}