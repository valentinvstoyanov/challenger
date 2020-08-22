package com.valentinvstoyanov.challenger

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import com.valentinvstoyanov.challenger.users.application.beans as userBeans
import com.valentinvstoyanov.challenger.challenges.application.beans as challengeBeans

@SpringBootApplication
class ChallengerApplication

fun main(args: Array<String>) {
    runApplication<ChallengerApplication>(*args) {
        addInitializers(userBeans())
        addInitializers(challengeBeans())
    }
}