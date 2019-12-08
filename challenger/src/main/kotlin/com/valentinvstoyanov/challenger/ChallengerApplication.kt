package com.valentinvstoyanov.challenger

import com.valentinvstoyanov.challenger.user.userConfig
import org.springframework.boot.WebApplicationType
import org.springframework.fu.kofu.KofuApplication
import org.springframework.fu.kofu.application
import org.springframework.fu.kofu.webflux.webFlux

val app: KofuApplication = application(WebApplicationType.REACTIVE) {
	enable(userConfig)
	webFlux {
		port = 8181
	}
}

fun main(args: Array<String>) {
	app.run(args)
}
