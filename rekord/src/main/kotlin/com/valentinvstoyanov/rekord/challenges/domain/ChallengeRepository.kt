package com.valentinvstoyanov.rekord.challenges.domain

import com.valentinvstoyanov.rekord.challenges.domain.model.Challenge
import com.valentinvstoyanov.rekord.challenges.domain.model.CreateChallenge
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface ChallengeRepository {
    fun create(challenge: CreateChallenge): Mono<Challenge>
    fun getById(id: String): Mono<Challenge>
    fun getAll(): Flux<Challenge>
}