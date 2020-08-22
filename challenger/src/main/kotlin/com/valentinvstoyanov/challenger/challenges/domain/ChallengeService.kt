package com.valentinvstoyanov.challenger.challenges.domain

import com.valentinvstoyanov.challenger.challenges.domain.model.Challenge
import com.valentinvstoyanov.challenger.challenges.domain.model.CreateChallenge
import reactor.core.publisher.Mono

interface ChallengeService {
    fun create(challenge: CreateChallenge): Mono<Challenge>
}

class ChallengeServiceImpl(
    private val challengeRepository: ChallengeRepository,
    private val challengeValidator: ChallengeValidator
) : ChallengeService {
    override fun create(challenge: CreateChallenge): Mono<Challenge> =
        challengeValidator.validate(challenge).flatMap { challengeRepository.create(it) }
}