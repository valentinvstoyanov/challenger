package com.valentinvstoyanov.rekord.users.domain.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY
import java.time.Instant

data class User(
    val id: String,
    val name: String,
    val email: String,
    val username: String,
    @JsonProperty(access = WRITE_ONLY) val password: String,
    val createdAt: Instant,
    @JsonIgnore val enabled: Boolean = true
)