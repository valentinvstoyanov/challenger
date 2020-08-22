package com.valentinvstoyanov.challenger.users.domain.model

import java.time.Instant

data class Token(val jwt: String) {
    companion object {
        const val BEARER = "Bearer "
    }
}
data class TokenContent(val userId: String, val username: String, val expiration: Instant)