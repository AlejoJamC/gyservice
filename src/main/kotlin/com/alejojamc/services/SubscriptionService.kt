package com.alejojamc.services

import com.alejojamc.entities.PauseRequest
import com.alejojamc.entities.Product
import com.alejojamc.repositories.ProductRepository
import com.alejojamc.repositories.SubscriptionRepository
import com.alejojamc.utils.loggerFor


class SubscriptionService(
    private val subscriptionRepository: SubscriptionRepository,
) {

    private val logger by lazy { loggerFor<SubscriptionService>() }

    suspend fun getSubscriptions(): List<Product> {
        logger.debug("On getSubscriptions.")

        return subscriptionRepository.getSubscriptions()
    }

    suspend fun getSubscriptionById(subscriptionId: Long): Subscription? {
        logger.debug("On getSubscriptionById [subscriptionId=${subscriptionId}]")

        return subscriptionRepository.getSubscriptionById(subscriptionId)
    }

    suspend fun insertSubscription(Subscription: Subscription) {
        logger.debug("On insertSubscription [${Subscription}]")

        return subscriptionRepository.insertSubscription(Subscription)
    }

    suspend fun updateSubscription(Subscription: Subscription) {
        logger.debug("On updateSubscription [${Subscription}]")

        return subscriptionRepository.updateSubscription(Subscription)
    }

    suspend fun pauseUnpauseSubscription(pauseRequest: PauseRequest) {
        logger.debug("On pauseUnpauseSubscription [${pauseRequest}]")

        return subscriptionRepository.pauseUnpauseSubscription(pauseRequest)
    }

    suspend fun deleteSubscription(SubscriptionId: Long) {
        logger.debug("On deleteSubscription [SubscriptionId=${SubscriptionId}]")

        return subscriptionRepository.deleteSubscription(SubscriptionId)
    }

}