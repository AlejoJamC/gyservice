package com.alejojamc.repositories

import com.alejojamc.entities.PauseRequest
import com.alejojamc.entities.Subscription
import com.alejojamc.exceptions.SubscriptionExceptions
import com.alejojamc.utils.DATETIME_FORMAT_POSTGRES
import com.alejojamc.utils.loggerFor
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import javax.sql.DataSource


open class SubscriptionRepository(private val dataSource: DataSource) {

    private val logger by lazy { loggerFor<SubscriptionRepository>() }

    open suspend fun insertSubscription(subscription: Subscription) {
        try {
            return dataSource.connection.use { con ->
                con.prepareStatement(INSERT_QUERY).use { pst ->
                    pst.setLong(1, subscription.productId)
                    pst.setLong(2, subscription.userId)
                    pst.setString(3, subscription.startDate!!)
                    pst.setString(4, subscription.endDate!!)
                    pst.setBoolean(5, subscription.isActive!!)
                    pst.setBoolean(6, false)
                    pst.executeUpdate()
                }
            }
        } catch (e: SQLException) {
            logger.error(e.message, e)
            throw SubscriptionExceptions.InsertError(subscription.productId.toString(), subscription.userId.toString())
        }
    }

    open suspend fun getSubscriptionById(subscriptionId: Long): Subscription? {
        var connection: Connection? = null
        var statement: PreparedStatement? = null
        var rs: ResultSet? = null
        try {
            connection = dataSource.connection
            statement = connection.prepareStatement(SELECT_BY_ID_QUERY)
            statement.setLong(1, subscriptionId)
            rs = statement.executeQuery()
            return if (rs.next()) {
                Subscription.fromResultSet(rs)
            } else null
        } catch (e: SQLException) {
            connection?.rollback()
            logger.error(e.message, e)
            throw SubscriptionExceptions.SelectSubscriptionByIdError(subscriptionId.toString())
        } finally {
            rs?.close()
            statement?.close()
            connection?.close()
        }
    }

    open suspend fun deleteSubscription(subscriptionId: Long) {
        try {
            return dataSource.connection.use { con ->
                con.prepareStatement(DELETE_QUERY).use { pst ->
                    pst.setLong(1, subscriptionId)
                    pst.executeUpdate()
                }
            }
        } catch (e: SQLException) {
            logger.error(e.message, e)
            throw SubscriptionExceptions.DeleteError(subscriptionId.toString())
        }
    }

    open suspend fun pauseUnpauseSubscription(pauseRequest: PauseRequest) {
        try {
            return dataSource.connection.use { con ->
                con.prepareStatement(UPDATE_STATE_QUERY).use { pst ->
                    pst.setBoolean(1, pauseRequest.state)
                    pst.setLong(2, pauseRequest.subscriptionId)
                    pst.executeUpdate()
                }
            }
        } catch (e: SQLException) {
            logger.error(e.message, e)
            throw SubscriptionExceptions.UpdateStateError(
                pauseRequest.subscriptionId.toString(),
                pauseRequest.state.toString()
            )
        }
    }

    open suspend fun getSubscriptionByUserId(userId: Long): List<Subscription> {
        var connection: Connection? = null
        var statement: PreparedStatement? = null
        var rs: ResultSet? = null

        try {
            connection = dataSource.connection
            statement = connection.prepareStatement(SELECT_BY_USER_ID_QUERY)
            rs = statement.executeQuery()
            val result = mutableListOf<Subscription>()
            while (rs.next()) {
                result.add(
                    Subscription.fromResultSet(rs)
                )
            }

            return result
        } catch (e: SQLException) {
            logger.error(e.message, e)
            throw SubscriptionExceptions.SelectSubscriptionByUserIdError(userId.toString())
        } finally {
            rs?.close()
            statement?.close()
            connection?.close()
        }
    }

    companion object {

        private const val INSERT_QUERY = """INSERT INTO subscriptions
                (product_id, user_id, start_date, end_date, is_active, is_deleted)
                VALUES (?,?,?,?,?,?)"""
        private const val SELECT_BY_ID_QUERY =
            """SELECT 
                id, product_id, user_id, start_date, end_date, is_active, is_deleted,
                to_char(created_at, '$DATETIME_FORMAT_POSTGRES') AS created_at,
                to_char(updated_at, '$DATETIME_FORMAT_POSTGRES') AS updated_at 
                FROM subscriptions WHERE id = ?"""
        private const val DELETE_QUERY =
            """UPDATE subscriptions SET
                is_active = false, is_deleted = true
                WHERE id = ?"""
        private const val SELECT_BY_USER_ID_QUERY =
            """SELECT 
                id, product_id, user_id, start_date, end_date, is_active, is_deleted,
                to_char(created_at, '$DATETIME_FORMAT_POSTGRES') AS created_at,
                to_char(updated_at, '$DATETIME_FORMAT_POSTGRES') AS updated_at 
                FROM subscriptions WHERE user_id = ? ORDER BY created_at DESC"""
        private const val UPDATE_STATE_QUERY =
            """UPDATE subscriptions SET
                is_active = ?
                WHERE id = ?"""

    }

}