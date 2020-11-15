package com.valentinvstoyanov.rekord.challenges.domain.model

data class CreateChallenge(
    val name: String,
    val description: String,
    val difficulty: Double,
    val createdBy: String
)