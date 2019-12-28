package com.valentinvstoyanov.challenger.user.application

import com.valentinvstoyanov.challenger.user.application.handler.UserHandler
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router

fun routes(userHandler: UserHandler): RouterFunction<ServerResponse> = router {
    "/api/users".nest {
        POST("/", userHandler::createUser)
        POST("/login", userHandler::loginUser)
//        PUT("/{id}", userHandler::updateUser)
//        DELETE("/{id}", userHandler::deleteUser)
        GET("/", userHandler::getAllUsers)
        GET("/{id}", userHandler::getUserById)
    }
}