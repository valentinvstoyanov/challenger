package com.valentinvstoyanov.rekord.users.domain

import org.springframework.security.crypto.password.PasswordEncoder as SpringSecurityPasswordEncoder

interface PasswordEncoder {
    operator fun invoke(password: String): String
    fun match(raw: String, encoded: String): Boolean
}

class SecurityPasswordEncoder(private val encoder: SpringSecurityPasswordEncoder) : PasswordEncoder {
    override fun invoke(password: String): String = encoder.encode(password)
    override fun match(raw: String, encoded: String): Boolean = encoder.matches(raw, encoded)
}