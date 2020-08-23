package com.valentinvstoyanov.challenger.challenges.application.handler

import com.valentinvstoyanov.challenger.challenges.domain.ChallengeService
import com.valentinvstoyanov.challenger.challenges.domain.model.CreateChallenge
import org.springframework.web.reactive.function.server.*
import reactor.core.publisher.Mono
import java.net.URI

class ChallengeHandler(private val challengeService: ChallengeService) {
    fun createChallenge(request: ServerRequest): Mono<ServerResponse> =
        request.bodyToMono<CreateChallenge>()
            .flatMap { challengeService.createChallenge(it) }
            .flatMap { ServerResponse.created(URI.create("${request.path()}/${it.id}")).json().bodyValue(it) }

    fun getChallengeById(request: ServerRequest): Mono<ServerResponse> =
        challengeService.getChallengeById(request.pathVariable("id")).flatMap { ServerResponse.ok().bodyValue(it) }

    fun getAllChallenges(request: ServerRequest): Mono<ServerResponse> =
        ServerResponse.ok().body(challengeService.getAllChallenges())
}