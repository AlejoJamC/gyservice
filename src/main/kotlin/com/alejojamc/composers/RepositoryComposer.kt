package com.alejojamc.composers

import com.alejojamc.redis.RedisClient
import com.alejojamc.repositories.CacheRepository
import com.alejojamc.repositories.ProductRepository
import com.typesafe.config.Config
import javax.sql.DataSource

class RepositoryComposer(config: Config, dataSource: DataSource, redisClient: RedisClient) {

    val productRepository = ProductRepository(dataSource)
    val cacheRepository = CacheRepository(redisClient)

}