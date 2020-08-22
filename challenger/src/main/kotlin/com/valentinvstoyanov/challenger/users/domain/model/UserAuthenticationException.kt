package com.valentinvstoyanov.challenger.users.domain.model

sealed class UserAuthenticationException : RuntimeException()
class TokenExpiredException : UserAuthenticationException()
class InvalidTokenException : UserAuthenticationException()