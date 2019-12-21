package com.valentinvstoyanov.challenger.user.domain.model

sealed class UserException : RuntimeException()
object UsernameAlreadyTakenException : UserException()
object EmailAlreadyTakenException : UserException()
object UserValidationException : UserException()