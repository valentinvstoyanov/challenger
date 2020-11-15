package com.valentinvstoyanov.rekord.records.domain.model

import java.time.Instant

data class Record(
    val id: String,
    val name: String,
    val description: String,
    val difficultyRank: Double,
    val interestRank: Double,
    val currentHolder: String?,
    val currentHolderAttempt: Attempt?,
    val attempts: Collection<Attempt>,
    val createdBy: String,
    val createdAt: Instant
)