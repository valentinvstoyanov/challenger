package com.valentinvstoyanov.challenger.challenges.data.entity

import org.springframework.data.annotation.Id
import java.time.Instant

data class DbChallenge(
    @Id val id: String?,
    val name: String,
    val description: String,
    val difficulty: Double,
    val popularity: Double,
    val isCompleted: Boolean,
    val completedBy: String?,
    val createdBy: String,
    val createdAt: Instant
)