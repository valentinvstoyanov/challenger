package com.valentinvstoyanov.challenger

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import com.valentinvstoyanov.challenger.user.beans as userBeans

@SpringBootApplication
class ChallengerApplication

fun main(args: Array<String>) {
    runApplication<ChallengerApplication>(*args) {
        addInitializers(userBeans())
    }
}