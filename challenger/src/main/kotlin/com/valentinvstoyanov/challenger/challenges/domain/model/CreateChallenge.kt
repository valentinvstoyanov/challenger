package com.valentinvstoyanov.challenger.challenges.domain.model

data class CreateChallenge(
    val name: String,
    val description: String,
    val difficulty: Double,
    val createdBy: String
)