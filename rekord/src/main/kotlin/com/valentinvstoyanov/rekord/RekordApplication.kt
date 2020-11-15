package com.valentinvstoyanov.rekord

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import com.valentinvstoyanov.rekord.users.application.beans as userBeans
import com.valentinvstoyanov.rekord.challenges.application.beans as challengeBeans

@SpringBootApplication
class RekordApplication

fun main(args: Array<String>) {
    runApplication<RekordApplication>(*args) {
        addInitializers(userBeans())
        addInitializers(challengeBeans())
    }
}