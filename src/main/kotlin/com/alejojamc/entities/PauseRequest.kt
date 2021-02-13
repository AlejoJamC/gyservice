package com.alejojamc.entities

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty


@JsonIgnoreProperties(ignoreUnknown = true)
data class PauseRequest(
    @JsonProperty("product_id") val productId: Long,
    val state: Boolean
)