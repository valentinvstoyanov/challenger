package com.valentinvstoyanov.challenger

import org.springframework.http.HttpStatus

data class ApiError(val status: HttpStatus, val message: String, val errors: List<ApiSubError>)
data class ApiSubError(val domain: String, val reason: String, val message: String)