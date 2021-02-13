package com.alejojamc.entities

import com.alejojamc.utils.ID_FIELD
import com.alejojamc.utils.IS_ACTIVE_FIELD
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming
import java.sql.ResultSet


@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
@JsonIgnoreProperties(ignoreUnknown = true)
data class Product(
    val id: Long?,
    val name: String?,
    val description: String?,
    val startDate: String?,
    val endDate: String?,
    val daysRange: Int?,
    val price: Double?,
    val tax: Double?,
    @get:JsonProperty(IS_ACTIVE_FIELD) val isActive: Boolean? = false,
    val createdAt: String?,
    val updatedAt: String?,
){

    companion object {
        private const val NAME_FIELD = "name"
        private const val DESCRIPTION_FIELD = "description"
        private const val START_DATE_FIELD = "start_date"
        private const val END_DATE_FIELD = "end_date"
        private const val DAYS_RANGE_FIELD = "days_range"
        private const val PRICE_FIELD = "price"
        private const val TAX_FIELD = "tax"
        private const val CREATED_AT_FIELD = "created_at"
        private const val UPDATED_AT_FIELD = "updated_at"

        fun fromResultSet(rs: ResultSet) = Product(
            id = rs.getLong(ID_FIELD),
            name = rs.getString(NAME_FIELD),
            description = rs.getString(DESCRIPTION_FIELD),
            startDate = rs.getString(START_DATE_FIELD),
            endDate = rs.getString(END_DATE_FIELD),
            daysRange = rs.getInt(DAYS_RANGE_FIELD),
            price = rs.getDouble(PRICE_FIELD),
            tax = rs.getDouble(TAX_FIELD),
            isActive = rs.getBoolean(IS_ACTIVE_FIELD),
            updatedAt = rs.getString(UPDATED_AT_FIELD),
            createdAt = rs.getString(CREATED_AT_FIELD)
        )
    }

}

