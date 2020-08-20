package com.valentinvstoyanov.challenger.user.domain

import com.valentinvstoyanov.challenger.user.domain.model.User
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import java.time.Instant
import java.util.*

internal class JwtTokenServiceTest {
    private val tokenService = JwtTokenService(
        Base64.getEncoder().encodeToString("secret must be longer and i think that this is a must, especially for test purposes".toByteArray()),
        24 * 60 * 60 * 1000
    )

    @Test
    fun parseToken() {
        val user = User(
            id = UUID.randomUUID().toString(),
            email = "test@test.com",
            name = "Test Name",
            username = "test username",
            password = "test password",
            createdAt = Instant.now()
        )

        val token = tokenService.createToken(user)
        val tokenContent = tokenService.parseToken(token).block()

        assertNotNull(tokenContent)
        assertEquals(tokenContent!!.userId, user.id)
        assertEquals(tokenContent.username, user.username)
    }
}