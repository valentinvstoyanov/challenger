package com.valentinvstoyanov.challenger.user

import com.valentinvstoyanov.challenger.user.handler.UserHandler
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router

fun routes(userHandler: UserHandler): RouterFunction<ServerResponse> = router {
    "/api/users".nest {
        POST("/", userHandler::createUser)
        PUT("/{id}", userHandler::updateUser)
        DELETE("/{id}", userHandler::deleteUser)
        GET("/", userHandler::getAllUsers)
        GET("/{id}", userHandler::getUserById)
    }
}