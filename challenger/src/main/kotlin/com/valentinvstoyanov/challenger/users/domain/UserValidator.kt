package com.valentinvstoyanov.challenger.users.domain

import com.valentinvstoyanov.challenger.users.domain.model.CreateUser
import com.valentinvstoyanov.challenger.users.domain.model.UpdateUser
import com.valentinvstoyanov.challenger.users.domain.model.User
import com.valentinvstoyanov.challenger.users.domain.model.UserValidationException
import io.konform.validation.Invalid
import io.konform.validation.Valid
import io.konform.validation.Validation
import io.konform.validation.jsonschema.pattern
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

interface UserValidator {
    fun validate(user: CreateUser): Mono<CreateUser>
    fun validate(user: User): Mono<User>
    fun validate(user: UpdateUser): Mono<UpdateUser>
}

class UserValidatorImpl : UserValidator {
    private val nameRegex = Regex("""^([a-zA-Z]+|[a-zA-Z]+\s{1}[a-zA-Z]{1,}|[a-zA-Z]+\s{1}[a-zA-Z]{3,}\s{1}[a-zA-Z]{1,})$""")
    private val usernameRegex = Regex("""^[a-zA-z0-9-._]{4,64}$""")
    private val emailRegex = Regex("""^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$""")
    private val passwordRegex = Regex("""^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{6,64}$""")

    private val nameHint = "has up to 3 space separated names"
    private val usernameHint = "should be at least 4 and at most 64 characters long consisting of letters, digits, '-', '.' and '_'"
    private val emailHint = "should match the email criteria"
    private val passwordHint = "should be at least 6 and at most 64 characters long containing at least one letter and at least on digit"

    private val validateCreateUser: Validation<CreateUser> = Validation {
        CreateUser::name { pattern(nameRegex) hint nameHint }
        CreateUser::username { pattern(usernameRegex) hint usernameHint }
        CreateUser::email { pattern(emailRegex) hint emailHint }
        CreateUser::password { pattern(passwordRegex) hint passwordHint }
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

    private val validateUser: Validation<User> = Validation {
        User::name { pattern(nameRegex) hint nameHint }
        User::username { pattern(usernameRegex) hint usernameHint }
        User::email { pattern(emailRegex) hint emailHint }
        User::password { pattern(passwordRegex) hint passwordHint }
    }

    override fun validate(user: User): Mono<User> =
        when (val result = validateUser(user)) {
            is Valid -> result.value.toMono()
            is Invalid -> {
                val message = listOf(
                    User::name,
                    User::username,
                    User::email,
                    User::password
                ).joinToString { "${it.name} ${result[it].orEmpty().joinToString()}." }
                UserValidationException(message).toMono()
            }
        }

    private val validateUpdateUser: Validation<UpdateUser> = Validation {
        UpdateUser::name ifPresent { pattern(nameRegex) hint nameHint }
        UpdateUser::username ifPresent { pattern(usernameRegex) hint usernameHint }
        UpdateUser::email ifPresent { pattern(emailRegex) hint emailHint }
        UpdateUser::newPassword ifPresent { pattern(passwordRegex) hint passwordHint }
    }

    override fun validate(user: UpdateUser): Mono<UpdateUser> =
        when (val result = validateUpdateUser(user)) {
            is Valid -> result.value.toMono()
            is Invalid -> {
                val message = listOf(
                    UpdateUser::name,
                    UpdateUser::username,
                    UpdateUser::email,
                    UpdateUser::newPassword
                ).joinToString { "${it.name} ${result[it].orEmpty().joinToString()}." }
                UserValidationException(message).toMono()
            }
        }
}