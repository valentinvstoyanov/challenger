package com.valentinvstoyanov.challenger.user.application

import com.valentinvstoyanov.challenger.user.application.handler.UserExceptionHandler
import com.valentinvstoyanov.challenger.user.application.handler.UserHandler
import com.valentinvstoyanov.challenger.user.application.security.AuthenticationManager
import com.valentinvstoyanov.challenger.user.application.security.SecurityConfig
import com.valentinvstoyanov.challenger.user.application.security.SecurityContextRepository
import com.valentinvstoyanov.challenger.user.data.PersistentUserRepository
import com.valentinvstoyanov.challenger.user.domain.JwtTokenService
import com.valentinvstoyanov.challenger.user.domain.SecurityPasswordEncoder
import com.valentinvstoyanov.challenger.user.domain.UserServiceImpl
import com.valentinvstoyanov.challenger.user.domain.UserValidatorImpl
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
    bean(SecurityConfig::springSecurityFilterChain)
    bean<UserExceptionHandler>()
    bean<UserHandler>()
    bean(::routes)
}