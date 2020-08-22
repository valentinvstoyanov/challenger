package com.valentinvstoyanov.challenger.challenges.application

import com.valentinvstoyanov.challenger.challenges.application.handler.ChallengeHandler
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router

fun routes(challengeHandler: ChallengeHandler): RouterFunction<ServerResponse> = router {
    "api/challenges".nest {
        POST("/", challengeHandler::createChallenge)
        GET("/", challengeHandler::getAllChallenges)
    }
}