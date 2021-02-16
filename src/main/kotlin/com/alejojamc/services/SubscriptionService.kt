package com.alejojamc.services

import com.alejojamc.entities.PauseRequest
import com.alejojamc.entities.Subscription
import com.alejojamc.repositories.SubscriptionRepository
import com.alejojamc.utils.loggerFor


class SubscriptionService(
    private val subscriptionRepository: SubscriptionRepository,
) {

    private val logger by lazy { loggerFor<SubscriptionService>() }

    suspend fun insertSubscription(Subscription: Subscription) {
        logger.debug("On insertSubscription [${Subscription}]")

        return subscriptionRepository.insertSubscription(Subscription)
    }

    suspend fun getSubscriptionById(subscriptionId: Long): Subscription? {
        logger.debug("On getSubscriptionById [subscriptionId=${subscriptionId}]")

        return subscriptionRepository.getSubscriptionById(subscriptionId)
    }

    suspend fun deleteSubscription(SubscriptionId: Long) {
        logger.debug("On deleteSubscription [SubscriptionId=${SubscriptionId}]")

        return subscriptionRepository.deleteSubscription(SubscriptionId)
    }

    suspend fun pauseUnpauseSubscription(pauseRequest: PauseRequest) {
        logger.debug("On pauseUnpauseSubscription [${pauseRequest}]")

        return subscriptionRepository.pauseUnpauseSubscription(pauseRequest)
    }

    suspend fun getSubscriptionByUserId(userId: Long): List<Subscription> {
        logger.debug("On getSubscriptionByUserId [userId=${userId}]")

        return subscriptionRepository.getSubscriptionByUserId(userId)
    }

}