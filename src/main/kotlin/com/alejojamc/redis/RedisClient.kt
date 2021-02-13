package com.alejojamc.redis

import com.alejojamc.entities.RedisConfig
import com.alejojamc.exceptions.RedisException
import com.typesafe.config.Config
import io.github.config4k.extract
import redis.clients.jedis.Jedis
import java.util.UUID


class RedisClient(config: Config) {

    private val host: String
    private val port: Int
    private lateinit var client: Jedis

    init {
        val redisConfig: RedisConfig = config.extract(REDIS_CONFIG)
        host = redisConfig.host
        port = redisConfig.port
    }

    private fun getRedisClient() = Jedis(host, port)

    fun getValue(key: String): String? = try {
        this.client = getRedisClient()
        this.client.get(key)
    } catch (e: Exception) {
        throw RedisException.ErrorGettingFromRedis()
    } finally {
        this.client.close()
    }

    fun getKeys(queryPattern: String): Set<String> {
        return getRedisClient().keys(queryPattern)
    }

    fun setObject(key: String, value: String, timeout: Long): String? {
        try {
            this.client = getRedisClient()
            var nxxx = "NX"
            if (client.exists(key)) nxxx = "XX"
            return this.client.set(key, value, nxxx, "EX", timeout)
        } catch (e: Exception) {
            throw RedisException.ErrorUpdateFromRedis()
        } finally {
            this.client.run { close() }
        }
    }

    fun setValue(value: String, timeout: Long): String? {
        try {
            val key = UUID.randomUUID().toString()
            setObject(key, value, timeout)
            return key
        } catch (e: Exception) {
            throw RedisException.ErrorUpdateFromRedis()
        }
    }

    fun deleteValue(key: String): Long? =
        try {
            this.client = getRedisClient()
            this.client.del(key)
        } catch (e: Exception) {
            throw RedisException.ErrorOnDeleteFromRedis()
        } finally {
            this.client.close()
        }

    companion object {
        private const val REDIS_CONFIG = "redisConfig"
    }
}