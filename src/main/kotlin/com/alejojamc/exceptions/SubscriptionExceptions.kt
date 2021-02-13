package com.alejojamc.exceptions

import com.alejojamc.entities.ApplicationError
import io.ktor.http.HttpStatusCode


sealed class SubscriptionExceptions(
    override var httpStatus: HttpStatusCode,
    override var code: String,
    override var args: List<String>? = null,
) : ApplicationError(httpStatus, code, args) {

    class SubscriptionBadRequest : SubscriptionExceptions(
        httpStatus = HttpStatusCode.BadRequest,
        code = "error.repository.subscription.bad.request"
    )

    class SubscriptionNotFound : SubscriptionExceptions(
        httpStatus = HttpStatusCode.BadRequest,
        code = "error.repository.subscription.not.found"
    )

    class SelectSubscriptionsError : SubscriptionExceptions(
        httpStatus = HttpStatusCode.InternalServerError,
        code = "error.repository.subscription.list"
    )

    class SelectSubscriptionByIdError(subscriptionId: String) : SubscriptionExceptions(
        httpStatus = HttpStatusCode.InternalServerError,
        code = "error.repository.subscription.by.id",
        args = listOf(subscriptionId)
    )

    class InsertError(subscriptionName: String) : SubscriptionExceptions(
        httpStatus = HttpStatusCode.InternalServerError,
        code = "error.repository.subscription.insert",
        args = listOf(subscriptionName)
    )

    class UpdateError(subscriptionId: String) : SubscriptionExceptions(
        httpStatus = HttpStatusCode.InternalServerError,
        code = "error.repository.subscription.update",
        args = listOf(subscriptionId)
    )

    class DeleteError(subscriptionId: String) : SubscriptionExceptions(
        httpStatus = HttpStatusCode.InternalServerError,
        code = "error.repository.subscription.delete",
        args = listOf(subscriptionId)
    )

}