package com.valentinvstoyanov.rekord.challenges.application

import com.valentinvstoyanov.rekord.challenges.application.handler.ChallengeExceptionHandler
import com.valentinvstoyanov.rekord.challenges.application.handler.ChallengeHandler
import com.valentinvstoyanov.rekord.challenges.data.PersistentChallengeRepository
import com.valentinvstoyanov.rekord.challenges.domain.ChallengeServiceImpl
import com.valentinvstoyanov.rekord.challenges.domain.ChallengeValidatorImpl
import org.springframework.context.support.BeanDefinitionDsl

fun beans(): BeanDefinitionDsl = org.springframework.context.support.beans {
    bean<PersistentChallengeRepository>()
    bean<ChallengeValidatorImpl>()
    bean<ChallengeServiceImpl>()
    bean<ChallengeExceptionHandler>()
    bean<ChallengeHandler>()
    bean(::routes)
}