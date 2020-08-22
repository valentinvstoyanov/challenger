package com.valentinvstoyanov.challenger.challenges.application

import com.valentinvstoyanov.challenger.challenges.domain.ChallengeService
import com.valentinvstoyanov.challenger.challenges.domain.model.CreateChallenge
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyToMono
import org.springframework.web.reactive.function.server.json
import reactor.core.publisher.Mono
import java.net.URI

class ChallengeHandler(private val challengeService: ChallengeService) {
    fun createChallenge(request: ServerRequest): Mono<ServerResponse> =
        request.bodyToMono<CreateChallenge>()
            .flatMap { challengeService.create(it) }
            .flatMap { ServerResponse.created(URI.create("${request.path()}/${it.id}")).json().bodyValue(it) }
}