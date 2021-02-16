package com.alejojamc.entities

import com.alejojamc.utils.CREATED_AT_FIELD
import com.alejojamc.utils.ID_FIELD
import com.alejojamc.utils.IS_ACTIVE_FIELD
import com.alejojamc.utils.IS_DELETED_FIELD
import com.alejojamc.utils.UPDATED_AT_FIELD
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming
import java.sql.ResultSet


@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
@JsonIgnoreProperties(ignoreUnknown = true)
data class Subscription(
    val id: Long?,
    val productId: Long,
    val userId: Long,
    val startDate: String?,
    val endDate: String?,
    @get:JsonProperty(IS_ACTIVE_FIELD) val isActive: Boolean? = false,
    @get:JsonProperty(IS_ACTIVE_FIELD) val isDeleted: Boolean? = null,
    val createdAt: String?,
    val updatedAt: String?,
) {

    companion object {
        private const val PRODUCT_ID = "product_id"
        private const val USER_ID = "user_id"
        private const val START_DATE_FIELD = "start_date"
        private const val END_DATE_FIELD = "end_date"

        fun fromResultSet(rs: ResultSet) = Subscription(
            id = rs.getLong(ID_FIELD),
            productId = rs.getLong(PRODUCT_ID),
            userId = rs.getLong(USER_ID),
            startDate = rs.getString(START_DATE_FIELD),
            endDate = rs.getString(END_DATE_FIELD),
            isActive = rs.getBoolean(IS_ACTIVE_FIELD),
            isDeleted = rs.getBoolean(IS_DELETED_FIELD),
            updatedAt = rs.getString(UPDATED_AT_FIELD),
            createdAt = rs.getString(CREATED_AT_FIELD)
        )
    }

}

