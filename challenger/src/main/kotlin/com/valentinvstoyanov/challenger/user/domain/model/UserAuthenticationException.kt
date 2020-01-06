package com.valentinvstoyanov.challenger.user.domain.model

sealed class UserAuthenticationException : RuntimeException()
class TokenExpiredException : UserAuthenticationException()
class InvalidTokenException : UserAuthenticationException()