package com.valentinvstoyanov.challenger.user.domain

import com.valentinvstoyanov.challenger.user.domain.model.CreateUser
import com.valentinvstoyanov.challenger.user.domain.model.UserValidationException
import io.konform.validation.Invalid
import io.konform.validation.Valid
import io.konform.validation.Validation
import io.konform.validation.jsonschema.pattern
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

interface UserValidator {
    fun validate(user: CreateUser): Mono<CreateUser>
}

class UserValidatorImpl : UserValidator {
    private val nameRegex: String = """^([a-zA-Z]+|[a-zA-Z]+\s{1}[a-zA-Z]{1,}|[a-zA-Z]+\s{1}[a-zA-Z]{3,}\s{1}[a-zA-Z]{1,})$"""
    private val usernameRegex: String = """^[a-zA-z0-9-._]{4,64}$"""
    private val emailRegex: String = """^[a-zA-Z0-9\.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$"""
    private val passwordRegex: String = """^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{6,64}$"""

    private val validateCreateUser: Validation<CreateUser> = Validation {
        CreateUser::name { pattern(nameRegex) hint "has up to 3 space separated names" }
        CreateUser::username { pattern(usernameRegex) hint "should be at least 4 and at most 64 characters long consisting of letters, digits, '-', '.' and '_'" }
        CreateUser::email { pattern(emailRegex) hint "should match the email criteria" }
        CreateUser::password { pattern(passwordRegex) hint "should be at least 6 and at most 64 characters long containing at least one letter and at least on digit" }
    }

    override fun validate(user: CreateUser): Mono<CreateUser> =
        when (val result = validateCreateUser(user)) {
            is Valid -> result.value.toMono()
            is Invalid -> {
                val message = listOf(
                    CreateUser::name,
                    CreateUser::username,
                    CreateUser::email,
                    CreateUser::password
                ).joinToString { "${it.name} ${result[it].orEmpty().joinToString()}." }
                UserValidationException(message).toMono()
            }
        }
}