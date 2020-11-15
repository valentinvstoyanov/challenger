package com.valentinvstoyanov.rekord.users.application

import com.valentinvstoyanov.rekord.users.application.handler.UserAuthenticationExceptionHandler
import com.valentinvstoyanov.rekord.users.application.handler.UserExceptionHandler
import com.valentinvstoyanov.rekord.users.application.handler.UserHandler
import com.valentinvstoyanov.rekord.users.application.security.AuthenticationManager
import com.valentinvstoyanov.rekord.users.application.security.SecurityConfig
import com.valentinvstoyanov.rekord.users.application.security.SecurityContextRepository
import com.valentinvstoyanov.rekord.users.application.security.UserDetailsService
import com.valentinvstoyanov.rekord.users.data.PersistentUserRepository
import com.valentinvstoyanov.rekord.users.domain.JwtTokenService
import com.valentinvstoyanov.rekord.users.domain.SecurityPasswordEncoder
import com.valentinvstoyanov.rekord.users.domain.UserServiceImpl
import com.valentinvstoyanov.rekord.users.domain.UserValidatorImpl
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