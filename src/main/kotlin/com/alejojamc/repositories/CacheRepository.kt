package com.alejojamc.repositories

import com.alejojamc.redis.RedisClient


class CacheRepository(private val redisClient: RedisClient) {

    fun getKeys(queryPattern: String): Set<String> {
        return redisClient.getKeys(queryPattern)
    }

    fun get(key: String): String? {
        return redisClient.getValue(key);
    }

    fun delete(key: String): Long? {
        return redisClient.deleteValue(key);
    }

}