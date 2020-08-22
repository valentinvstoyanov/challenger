package com.valentinvstoyanov.challenger.challenges.domain

import com.valentinvstoyanov.challenger.challenges.domain.model.Challenge
import com.valentinvstoyanov.challenger.challenges.domain.model.CreateChallenge
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface ChallengeRepository {
    fun create(challenge: CreateChallenge): Mono<Challenge>
    fun getAll(): Flux<Challenge>
}