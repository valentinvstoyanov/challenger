package com.valentinvstoyanov.rekord.challenges.data.entity

import com.valentinvstoyanov.rekord.challenges.domain.model.Challenge
import com.valentinvstoyanov.rekord.challenges.domain.model.CreateChallenge
import org.springframework.data.annotation.Id
import java.time.Instant

data class DbChallenge(
    @Id val id: String?,
    val name: String,
    val description: String,
    val difficulty: Double,
    val popularity: Double?,
    val isCompleted: Boolean,
    val completedBy: String?,
    val createdBy: String,
    val createdAt: Instant
) {
    companion object {
        fun from(challenge: CreateChallenge): DbChallenge = DbChallenge(
            id = null,
            name = challenge.name,
            description = challenge.description,
            difficulty = challenge.difficulty,
            popularity = null,
            isCompleted = false,
            completedBy = null,
            createdAt = Instant.now(),
            createdBy = challenge.createdBy
        )

        fun from(challenge: Challenge): DbChallenge = DbChallenge(
            id = challenge.id,
            name = challenge.name,
            description = challenge.description,
            difficulty = challenge.difficulty,
            popularity = challenge.popularity,
            isCompleted = challenge.isCompleted,
            completedBy = challenge.completedBy,
            createdAt = challenge.createdAt,
            createdBy = challenge.createdBy
        )
    }

    fun toChallenge(): Challenge = Challenge(
        id = id!!,
        name = name,
        description = description,
        difficulty = difficulty,
        popularity = popularity ?: 0.0,
        isCompleted = isCompleted,
        completedBy = completedBy,
        createdBy = createdBy,
        createdAt = createdAt
    )
}