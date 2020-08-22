package com.valentinvstoyanov.challenger.challenges.domain.model

import java.lang.RuntimeException

sealed class ChallengeException : RuntimeException()
data class ChallengeValidationException(val hint: String) : ChallengeException()