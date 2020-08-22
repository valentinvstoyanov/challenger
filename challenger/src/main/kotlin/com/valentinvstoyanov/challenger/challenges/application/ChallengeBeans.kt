package com.valentinvstoyanov.challenger.challenges.application

import com.valentinvstoyanov.challenger.challenges.data.PersistentChallengeRepository
import com.valentinvstoyanov.challenger.challenges.domain.ChallengeServiceImpl
import com.valentinvstoyanov.challenger.challenges.domain.ChallengeValidatorImpl
import org.springframework.context.support.BeanDefinitionDsl

fun beans(): BeanDefinitionDsl = org.springframework.context.support.beans {
    bean<PersistentChallengeRepository>()
    bean<ChallengeValidatorImpl>()
    bean<ChallengeServiceImpl>()
    bean<ChallengeHandler>()
    bean(::routes)
}