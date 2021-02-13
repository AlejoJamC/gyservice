package com.alejojamc.config

import com.alejojamc.entities.DataSourceConfig
import com.typesafe.config.Config
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.github.config4k.extract
import org.flywaydb.core.Flyway
import javax.sql.DataSource


class RepositoryConfig(config: Config) {
    var dataSource: DataSource

    init {
        val dataSourceConfig: DataSourceConfig = config.extract(DATABASE_CONFIG)
        dataSource = getDataSource(dataSourceConfig)
        setupFlyway(dataSource)
    }

    private fun getDataSource(dbConfig: DataSourceConfig) = HikariDataSource(
        HikariConfig().apply {
            setDriverClassName(dbConfig.driverClassName)
            jdbcUrl = dbConfig.jdbcUrl
            maximumPoolSize = dbConfig.poolSize
            minimumIdle = dbConfig.poolSize / 2
            connectionTestQuery = CHECK_STATEMENT
            connectionTimeout = 10000
            idleTimeout = 60000
            minimumIdle = 5
            leakDetectionThreshold = 10000
            maxLifetime = 900000
            if (dbConfig.user.isNotBlank()) {
                username = dbConfig.user
                password = dbConfig.password
            }
            addDataSourceProperty("cachePrepStmts", "true")
            addDataSourceProperty("prepStmtCacheSize", "20")
            addDataSourceProperty("prepStmtCacheSqlLimit", "40")
        })

    private fun setupFlyway(dataSource: DataSource) {
        val flyway = Flyway.configure().dataSource(dataSource).load()
        flyway.migrate()
    }

    companion object {
        private const val CHECK_STATEMENT = "SELECT 1"
        private const val DATABASE_CONFIG = "dataBaseConfig"
    }
}
