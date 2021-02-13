package com.alejojamc.entities

import io.ktor.http.HttpStatusCode

data class ApiError(val httpStatus: HttpStatusCode, val error: ErrorInfo)

data class ErrorInfo(val code: String, val message: String)

open class ApplicationError(
    open var httpStatus: HttpStatusCode,
    open var code: String,
    open var args: List<String>? = null,
) : RuntimeException()