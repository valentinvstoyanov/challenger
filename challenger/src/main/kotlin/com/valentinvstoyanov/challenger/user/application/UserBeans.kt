package com.valentinvstoyanov.challenger.user.application

import com.valentinvstoyanov.challenger.user.data.PersistentUserRepository
import com.valentinvstoyanov.challenger.user.domain.UserServiceImpl
import com.valentinvstoyanov.challenger.user.domain.UserValidatorImpl
import org.springframework.context.support.BeanDefinitionDsl
import org.springframework.context.support.beans

fun beans(): BeanDefinitionDsl = beans {
    bean<PersistentUserRepository>()
    bean<UserValidatorImpl>()
    bean<UserServiceImpl>()
    bean<UserHandler>()
    bean(::routes)
}