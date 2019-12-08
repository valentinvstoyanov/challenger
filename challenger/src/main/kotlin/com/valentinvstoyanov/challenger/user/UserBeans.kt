package com.valentinvstoyanov.challenger.user

import com.valentinvstoyanov.challenger.user.domain.usecase.*
import com.valentinvstoyanov.challenger.user.handler.UserHandler
import com.valentinvstoyanov.challenger.user.infrastructure.persistence.PersistentUserRepository
import org.springframework.context.support.BeanDefinitionDsl
import org.springframework.context.support.beans

fun beans(): BeanDefinitionDsl = beans {
    bean<PersistentUserRepository>()
    bean<RegisterUseCase>()
    bean<LoginUseCase>()
    bean<DeleteUserUseCase>()
    bean<GetAllUsersUseCase>()
    bean<GetUserByIdUseCase>()
    bean<UpdateUserUseCase>()
    bean<UserHandler>()
    bean(::routes)
}