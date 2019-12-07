package com.valentinvstoyanov.challenger

import org.springframework.boot.WebApplicationType
import org.springframework.fu.kofu.application
import org.springframework.fu.kofu.webflux.webFlux

val app = application(WebApplicationType.REACTIVE) {
	webFlux {
		port = 8181
	}
}

fun main(args: Array<String>) {
	app.run(args)
}
