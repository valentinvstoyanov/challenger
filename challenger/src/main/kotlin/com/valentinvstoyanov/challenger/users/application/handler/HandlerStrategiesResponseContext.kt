package com.valentinvstoyanov.challenger.users.application.handler

import org.springframework.http.codec.HttpMessageWriter
import org.springframework.web.reactive.function.server.HandlerStrategies
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.result.view.ViewResolver

class HandlerStrategiesResponseContext(private val handlerStrategies: HandlerStrategies) : ServerResponse.Context {
    override fun viewResolvers(): MutableList<ViewResolver> = handlerStrategies.viewResolvers()

    override fun messageWriters(): MutableList<HttpMessageWriter<*>> = handlerStrategies.messageWriters()
}