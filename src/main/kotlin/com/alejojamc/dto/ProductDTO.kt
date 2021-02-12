package com.alejojamc.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class ProductDTO(
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