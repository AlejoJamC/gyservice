package com.alejojamc.config

import com.alejojamc.composers.ControllerComposer
import com.fasterxml.jackson.databind.SerializationFeature
import com.typesafe.config.Config
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.jackson.jackson
import io.ktor.routing.route
import io.ktor.routing.routing


class WebServerConfig(
    application: Application,
    config: Config,
) {

    init {
        application.apply {
            install(ContentNegotiation) {
                jackson {
                    enable(SerializationFeature.INDENT_OUTPUT)
                }
            }

            routing {
                route("/api/gy") {
                    ControllerComposer(config = config, route = this)
                }
            }
        }
    }

}