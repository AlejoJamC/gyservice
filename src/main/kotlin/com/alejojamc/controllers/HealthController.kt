package com.alejojamc.controllers

import com.alejojamc.utils.GYSERVICE
import com.alejojamc.entities.Gyservice
import com.typesafe.config.Config
import io.github.config4k.extract
import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.route

class HealthController(
    config: Config,
    route: Route,
) {
    private val version: String

    init {

        val gyserviceSettings: Gyservice = config.extract(GYSERVICE)
        version = gyserviceSettings.version

        route.route("/health") {
            get {
                call.respond(mapOf("status" to "UP", "version" to version))
            }
        }

    }
}