package com.valentinvstoyanov.challenger.challenges.application

import com.valentinvstoyanov.challenger.challenges.application.handler.ChallengeExceptionHandler
import com.valentinvstoyanov.challenger.challenges.application.handler.ChallengeHandler
import com.valentinvstoyanov.challenger.challenges.data.PersistentChallengeRepository
import com.valentinvstoyanov.challenger.challenges.domain.ChallengeServiceImpl
import com.valentinvstoyanov.challenger.challenges.domain.ChallengeValidatorImpl
import org.springframework.context.support.BeanDefinitionDsl

fun beans(): BeanDefinitionDsl = org.springframework.context.support.beans {
    bean<PersistentChallengeRepository>()
    bean<ChallengeValidatorImpl>()
    bean<ChallengeServiceImpl>()
    bean<ChallengeExceptionHandler>()
    bean<ChallengeHandler>()
    bean(::routes)
}