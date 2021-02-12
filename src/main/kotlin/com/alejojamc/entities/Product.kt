package com.alejojamc.entities

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
@JsonIgnoreProperties(ignoreUnknown = true)
data class Product(
    val id: Long,
    val name: String,
    val description: String,
    val startDate: String,
    val endDate: String,
    val daysRange: Int,
    val price: Double,
    val tax: Double,
    @get:JsonProperty("is_active") val isActive: Boolean? = false,
    val createdAt: String?,
    val updatedAt: String?,
)