package com.valentinvstoyanov.challenger.user.domain.model

sealed class UserException : RuntimeException()
data class UsernameAlreadyTakenException(val username: String) : UserException()
data class EmailAlreadyTakenException(val email: String) : UserException()
data class UserValidationException(val hint: String) : UserException()
data class UserNotFoundException(val propertyName: String, val propertyValue: String) : UserException()
data class UserLoginException(val emailOrUsername: String): UserException()