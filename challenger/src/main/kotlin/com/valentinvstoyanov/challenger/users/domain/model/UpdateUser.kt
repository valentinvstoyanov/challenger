package com.valentinvstoyanov.challenger.users.domain.model

data class UpdateUser(
    val name: String? = null,
    val email: String? = null,
    val username: String? = null,
    val oldPassword: String? = null,
    val newPassword: String? = null
) {
    val hasChanges: Boolean = name != null || email != null || username != null || newPassword != null
}