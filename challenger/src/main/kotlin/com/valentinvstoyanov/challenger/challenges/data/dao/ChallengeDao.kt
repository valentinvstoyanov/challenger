package com.valentinvstoyanov.challenger.challenges.data.dao

import com.valentinvstoyanov.challenger.challenges.data.entity.DbChallenge
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface ChallengeDao : ReactiveMongoRepository<DbChallenge, String> {
}