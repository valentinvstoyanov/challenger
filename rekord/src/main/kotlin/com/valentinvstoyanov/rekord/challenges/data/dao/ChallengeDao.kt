package com.valentinvstoyanov.rekord.challenges.data.dao

import com.valentinvstoyanov.rekord.challenges.data.entity.DbChallenge
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface ChallengeDao : ReactiveMongoRepository<DbChallenge, String> {
}