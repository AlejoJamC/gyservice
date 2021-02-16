package com.alejojamc.entities

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty


@JsonIgnoreProperties(ignoreUnknown = true)
data class PauseRequest(
    @JsonProperty("subscription_id") val subscriptionId: Long,
    val state: Boolean
)