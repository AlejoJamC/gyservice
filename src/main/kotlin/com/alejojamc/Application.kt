package com.alejojamc

import com.alejojamc.config.HttpClientConfig
import com.alejojamc.config.RepositoryConfig
import com.alejojamc.config.WebServerConfig
import com.typesafe.config.ConfigFactory
import io.ktor.application.*


fun main(args: Array<String>): Unit = io.ktor.server.jetty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    val config = ConfigFactory.load()
    val httpClientConfig = HttpClientConfig(config = config)
    val repositoryConfig = RepositoryConfig(config = config)

    WebServerConfig(application = this, config = config)
}

