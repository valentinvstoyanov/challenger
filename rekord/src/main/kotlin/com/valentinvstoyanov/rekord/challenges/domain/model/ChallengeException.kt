package com.valentinvstoyanov.rekord.challenges.domain.model

sealed class ChallengeException : RuntimeException()
data class ChallengeValidationException(val hint: String) : ChallengeException()
data class ChallengeNotFoundException(val id: String) : ChallengeException()