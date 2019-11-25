package com.valentinvstoyanov.challenger

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ChallengerApplication

fun main(args: Array<String>) {
	runApplication<ChallengerApplication>(*args)
}
