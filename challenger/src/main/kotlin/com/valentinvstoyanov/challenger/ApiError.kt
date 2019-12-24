package com.valentinvstoyanov.challenger

data class ApiError(val statusCode: Int, val message: String, val errors: List<ApiSubError>)
data class ApiSubError(val domain: String, val reason: String, val message: String)