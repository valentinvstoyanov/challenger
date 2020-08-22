package com.valentinvstoyanov.challenger.challenges.domain

import com.valentinvstoyanov.challenger.challenges.domain.model.ChallengeValidationException
import com.valentinvstoyanov.challenger.challenges.domain.model.CreateChallenge
import io.konform.validation.Invalid
import io.konform.validation.Valid
import io.konform.validation.Validation
import io.konform.validation.jsonschema.maxLength
import io.konform.validation.jsonschema.minLength
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

interface ChallengeValidator {
    fun validate(challenge: CreateChallenge): Mono<CreateChallenge>
}

class ChallengeValidatorImpl : ChallengeValidator {
    private val nameHint = "should be at least 4 and at most 64 characters long"
    private val descriptionHint = "should be at least 8 and at most 256 characters long"

    private val validateCreateChallenge: Validation<CreateChallenge> = Validation {
        CreateChallenge::name {
            minLength(4) hint nameHint
            maxLength(64) hint nameHint
        }

        CreateChallenge::description {
            minLength(8) hint descriptionHint
            maxLength(256) hint descriptionHint
        }
    }

    override fun validate(challenge: CreateChallenge): Mono<CreateChallenge> =
        when (val result = validateCreateChallenge(challenge)) {
            is Valid -> result.value.toMono()
            is Invalid -> {
                val message = listOf(
                    CreateChallenge::name,
                    CreateChallenge::description
                ).joinToString { "${it.name} ${result[it].orEmpty().joinToString()}." }
                ChallengeValidationException(message).toMono()
            }
        }
}