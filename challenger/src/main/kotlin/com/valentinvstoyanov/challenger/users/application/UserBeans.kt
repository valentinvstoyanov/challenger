package com.valentinvstoyanov.challenger.users.application

import com.valentinvstoyanov.challenger.users.application.handler.UserAuthenticationExceptionHandler
import com.valentinvstoyanov.challenger.users.application.handler.UserExceptionHandler
import com.valentinvstoyanov.challenger.users.application.handler.UserHandler
import com.valentinvstoyanov.challenger.users.application.security.AuthenticationManager
import com.valentinvstoyanov.challenger.users.application.security.SecurityConfig
import com.valentinvstoyanov.challenger.users.application.security.SecurityContextRepository
import com.valentinvstoyanov.challenger.users.application.security.UserDetailsService
import com.valentinvstoyanov.challenger.users.data.PersistentUserRepository
import com.valentinvstoyanov.challenger.users.domain.JwtTokenService
import com.valentinvstoyanov.challenger.users.domain.SecurityPasswordEncoder
import com.valentinvstoyanov.challenger.users.domain.UserServiceImpl
import com.valentinvstoyanov.challenger.users.domain.UserValidatorImpl
import org.springframework.context.support.BeanDefinitionDsl
import org.springframework.context.support.beans
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

fun beans(): BeanDefinitionDsl = beans {
    bean<PersistentUserRepository>()
    bean<UserValidatorImpl>()
    //TODO: extract secret and expiration
    bean { JwtTokenService("change this secret and extract it", 24 * 60 * 60 * 1000) }
    bean<BCryptPasswordEncoder>()
    bean<SecurityPasswordEncoder>()
    bean<UserServiceImpl>()
    bean<AuthenticationManager>()
    bean<SecurityContextRepository>()
    bean<UserDetailsService>()
    bean(SecurityConfig::springSecurityFilterChain)
    bean<UserExceptionHandler>()
    bean<UserAuthenticationExceptionHandler>()
    bean<UserHandler>()
    bean(::routes)
}