package com.valentinvstoyanov.challenger.challenges.domain

import com.valentinvstoyanov.challenger.challenges.domain.model.Challenge
import com.valentinvstoyanov.challenger.challenges.domain.model.ChallengeNotFoundException
import com.valentinvstoyanov.challenger.challenges.domain.model.CreateChallenge
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty
import reactor.kotlin.core.publisher.toMono

interface ChallengeService {
    fun createChallenge(challenge: CreateChallenge): Mono<Challenge>

    fun getChallengeById(id: String): Mono<Challenge>
    fun getAllChallenges(): Flux<Challenge>
}

class ChallengeServiceImpl(
    private val challengeRepository: ChallengeRepository,
    private val challengeValidator: ChallengeValidator
) : ChallengeService {
    override fun createChallenge(challenge: CreateChallenge): Mono<Challenge> =
        challengeValidator.validate(challenge).flatMap { challengeRepository.create(it) }

    override fun getChallengeById(id: String): Mono<Challenge> =
        challengeRepository.getById(id).switchIfEmpty { ChallengeNotFoundException(id).toMono() }

    override fun getAllChallenges(): Flux<Challenge> = challengeRepository.getAll()
}