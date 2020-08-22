package com.valentinvstoyanov.challenger.challenges.domain.model

sealed class ChallengeException : RuntimeException()
data class ChallengeValidationException(val hint: String) : ChallengeException()