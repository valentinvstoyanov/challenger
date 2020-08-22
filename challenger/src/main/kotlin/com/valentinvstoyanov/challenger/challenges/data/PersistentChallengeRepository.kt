package com.valentinvstoyanov.challenger.challenges.data

import com.valentinvstoyanov.challenger.challenges.data.dao.ChallengeDao
import com.valentinvstoyanov.challenger.challenges.data.entity.DbChallenge
import com.valentinvstoyanov.challenger.challenges.domain.ChallengeRepository
import com.valentinvstoyanov.challenger.challenges.domain.model.Challenge
import com.valentinvstoyanov.challenger.challenges.domain.model.CreateChallenge
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

class PersistentChallengeRepository(private val challengeDao: ChallengeDao) : ChallengeRepository {
    override fun create(challenge: CreateChallenge): Mono<Challenge> =
        challengeDao.insert(DbChallenge.from(challenge)).map { it.toChallenge() }

    override fun getAll(): Flux<Challenge> = challengeDao.findAll().map { it.toChallenge() }
}