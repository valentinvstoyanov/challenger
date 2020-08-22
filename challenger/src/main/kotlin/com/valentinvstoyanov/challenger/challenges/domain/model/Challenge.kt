package com.valentinvstoyanov.challenger.challenges.domain.model

import java.time.Instant

data class Challenge(
    val id: String,
    val name: String,
    val description: String,
    val difficulty: Double,
    val popularity: Double,
    val isCompleted: Boolean,
    val completedBy: String?,
    val createdBy: String,
    val createdAt: Instant
)