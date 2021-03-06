package com.alejojamc.entities

import com.alejojamc.utils.EN

data class DataSourceConfig(
    val host: String,
    val name: String,
    val user: String,
    val password: String,
    val port: Int,
    val driverClassName: String,
    val poolSize: Int,
) {
    var jdbcUrl: String = "jdbc:postgresql://${host}:${port}/${name}?socketTimeout=120"

    init {
        require(host.isNotBlank()) { "DB host must not be null" }
        require(name.isNotBlank()) { "DB name must not be null" }
        require(user.isNotBlank()) { "DB user must not be null" }
        require(password.isNotBlank()) { "DB password must not be null" }
        require(driverClassName.isNotBlank()) { "DB driverClassName must not be null" }
    }
}

data class Gyservice(
    val version: String,
    val country: String,
    val locale: String? = EN,
) {
    init {
        require(version.isNotBlank()) { "Gyservice version must not be blank" }
    }
}

data class RedisConfig(
    val host: String,
    val port: Int,
) {
    init {
        require(host.isNotBlank()) { "redis host must not be blank" }
    }
}
