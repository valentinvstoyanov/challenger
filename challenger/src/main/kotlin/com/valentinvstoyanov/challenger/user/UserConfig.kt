package com.valentinvstoyanov.challenger.user

import com.valentinvstoyanov.challenger.user.handler.UserHandler
import org.springframework.fu.kofu.ConfigurationDsl
import org.springframework.fu.kofu.configuration

val userConfig: ConfigurationDsl = configuration {
    beans {
        bean<UserHandler>()
        bean(::userRoutes)
    }
}