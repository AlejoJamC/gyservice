package com.alejojamc

import com.alejojamc.composers.RepositoryComposer
import com.alejojamc.composers.ServiceComposer
import com.alejojamc.config.RepositoryConfig
import com.alejojamc.config.WebServerConfig
import com.alejojamc.redis.RedisClient
import com.typesafe.config.ConfigFactory
import io.ktor.application.*


fun main(args: Array<String>): Unit = io.ktor.server.jetty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    val config = ConfigFactory.load()
    val repositoryConfig = RepositoryConfig(config = config)
    val redisClient = RedisClient(config)
    val repositoryComposer =
        RepositoryComposer(config = config, dataSource = repositoryConfig.dataSource, redisClient = redisClient)
    val serviceComposer = ServiceComposer(config = config, repositoryComposer)

    WebServerConfig(application = this, config = config, serviceComposer = serviceComposer)
}

