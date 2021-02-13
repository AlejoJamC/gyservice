package com.alejojamc.exceptions

import com.alejojamc.entities.ApplicationError
import io.ktor.http.HttpStatusCode

sealed class RedisException(
    override var httpStatus: HttpStatusCode,
    override var code: String,
    override var args: List<String>? = null,
) : ApplicationError(httpStatus, code, args) {

    class ErrorGettingFromRedis : RedisException(HttpStatusCode.InternalServerError, "error.redis.get")

    class ErrorUpdateFromRedis : RedisException(HttpStatusCode.InternalServerError, "error.redis.update")

    class ErrorOnDeleteFromRedis : RedisException(HttpStatusCode.InternalServerError, "error.redis.delete")

}