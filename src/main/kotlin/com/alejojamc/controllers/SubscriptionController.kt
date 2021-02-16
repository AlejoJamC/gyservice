package com.alejojamc.controllers

import com.alejojamc.entities.PauseRequest
import com.alejojamc.entities.Subscription
import com.alejojamc.exceptions.SubscriptionExceptions
import com.alejojamc.services.SubscriptionService
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.*


class SubscriptionController(
    route: Route,
    private val subscriptionService: SubscriptionService
) {

    init {
        route.route("/subscriptions") {
            post {
                val product = call.receive<Subscription>()
                call.respond(HttpStatusCode.Created, insertSubscription(product))
            }

            route("/{subscriptionId}") {
                get {
                    val subId = call.parameters[SUBSCRIPTION_ID_PARAM]
                    call.respond(HttpStatusCode.OK, getSubscriptionById(subId))
                }
                delete {
                    val productId = call.parameters[SUBSCRIPTION_ID_PARAM]
                    deleteSubscription(productId)
                    call.respond(HttpStatusCode.NoContent)
                }
            }

            route("/state") {
                put {
                    val request = call.receive<PauseRequest>()
                    pauseUnpauseSubscription(request)
                    call.respond(HttpStatusCode.NoContent)
                }
            }

            route("/users/{userId}") {
                get {
                    val userId = call.parameters[USER_ID_PARAM]
                    call.respond(HttpStatusCode.OK, getSubscriptionByUserId(userId))
                }

            }
        }
    }

    private suspend fun insertSubscription(subscription: Subscription) {
        return subscriptionService.insertSubscription(subscription)
    }

    private suspend fun getSubscriptionById(subsId: String?): Subscription {
        return subsId?.let {
            subscriptionService.getSubscriptionById(it.toLong()) ?: throw SubscriptionExceptions.SubscriptionNotFound()
        } ?: throw SubscriptionExceptions.SubscriptionBadRequest()
    }

    private suspend fun deleteSubscription(subsId: String?) {
        return subsId?.let {
            subscriptionService.deleteSubscription(it.toLong())
        } ?: throw SubscriptionExceptions.SubscriptionBadRequest()
    }

    private suspend fun pauseUnpauseSubscription(pauseRequest: PauseRequest) {
        return subscriptionService.pauseUnpauseSubscription(pauseRequest)
    }

    private suspend fun getSubscriptionByUserId(userId: String?): List<Subscription> {
        return userId?.let {
            subscriptionService.getSubscriptionByUserId(it.toLong())
        } ?: throw SubscriptionExceptions.SubscriptionBadRequest()
    }

    companion object {
        const val SUBSCRIPTION_ID_PARAM = "subscriptionId"
        const val USER_ID_PARAM = "userId"
    }

}