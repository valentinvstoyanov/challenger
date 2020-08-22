package com.valentinvstoyanov.challenger.challenges.data.dao

import com.valentinvstoyanov.challenger.users.data.entity.DbUser
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface ChallengeDao : ReactiveMongoRepository<DbUser, String> {
}