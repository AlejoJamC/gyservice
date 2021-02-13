package com.alejojamc.repositories

import com.alejojamc.entities.Product
import com.alejojamc.exceptions.ProductExceptions
import com.alejojamc.utils.DATETIME_FORMAT_POSTGRES
import com.alejojamc.utils.loggerFor
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import javax.sql.DataSource


open class ProductRepository(private val dataSource: DataSource) {

    private val logger by lazy { loggerFor<ProductRepository>() }

    open suspend fun getProduct(id: Long): List<Product> {
        try {
            return dataSource.connection.use { con ->
                con.prepareStatement(SELECT_QUERY).use { pst ->
                    pst.setLong(1, id)
                    pst.executeQuery().use { rs ->
                        rs.use {
                            generateSequence {
                                Product.fromResultSet(rs)
                            }.toList()
                        }
                    }
                }
            }
        } catch (e: SQLException) {
            logger.error(e.message, e)
            throw ProductExceptions.SelectProductsError()
        }
    }

    fun getProductById(productId: Long): Product? {
        var connection: Connection? = null
        var statement: PreparedStatement? = null
        var rs: ResultSet? = null

        try {
            connection = dataSource.connection
            statement = connection.prepareStatement(SELECT_BY_ID_QUERY)
            statement.setString(1, productId.toString())
            rs = statement.executeQuery()
            return if (rs.next()) {
                Product.fromResultSet(rs)
            } else null
        } catch (e: SQLException) {
            connection?.rollback()
            logger.error(e.message, e)
            throw ProductExceptions.SelectProductByIdError(productId.toString())
        } finally {
            rs?.close()
            statement?.close()
            connection?.close()
        }
    }

    open suspend fun insertProduct(product: Product) {
        try {
            return dataSource.connection.use { con ->
                con.prepareStatement(INSERT_QUERY).use { pst ->
                    pst.setString(1, product.name)
                    pst.setString(2, product.description)
                    pst.setString(3, product.startDate)
                    pst.setString(4, product.endDate)
                    pst.setInt(5, product.daysRange!!)
                    pst.setDouble(6, product.price!!)
                    pst.setDouble(7, product.tax!!)
                    pst.setBoolean(8, product.isActive ?: true)
                    pst.executeUpdate()
                }
            }
        } catch (e: SQLException) {
            logger.error(e.message, e)
            throw ProductExceptions.InsertError(product.name ?: "Error Name")
        }
    }

    open suspend fun updateProduct(product: Product) {
        try {
            return dataSource.connection.use { con ->
                con.prepareStatement(UPDATE_QUERY).use { pst ->
                    pst.setString(1, product.name)
                    pst.setString(2, product.description)
                    pst.setString(3, product.startDate)
                    pst.setString(4, product.endDate)
                    pst.setInt(5, product.daysRange ?: 0)
                    pst.setDouble(6, product.price ?: 0.0)
                    pst.setDouble(7, product.tax ?: 0.0)
                    pst.setBoolean(8, product.isActive ?: true)
                    pst.setLong(9, product.id!!)
                    pst.executeUpdate()
                }
            }
        } catch (e: SQLException) {
            logger.error(e.message, e)
            throw ProductExceptions.UpdateError(product.id.toString())
        }
    }

    open suspend fun deleteProduct(productId: Long) {
        try {
            return dataSource.connection.use { con ->
                con.prepareStatement(DELETE_QUERY).use { pst ->
                    pst.setLong(1, productId)
                    pst.executeUpdate()
                }
            }
        } catch (e: SQLException) {
            logger.error(e.message, e)
            throw ProductExceptions.DeleteError(productId.toString())
        }
    }

    companion object {

        const val SELECT_QUERY =
            """SELECT 
                id, name, description, start_date, end_date,
                days_range, price, tax, is_active, 
                to_char(created_at, '$DATETIME_FORMAT_POSTGRES') AS created_at,
                to_char(updated_at, '$DATETIME_FORMAT_POSTGRES') AS updated_at 
                FROM products WHERE is_active = TRUE"""
        const val SELECT_BY_ID_QUERY =
            """SELECT 
                id, name, description, start_date, end_date,
                days_range, price, tax, is_active, 
                to_char(created_at, '$DATETIME_FORMAT_POSTGRES') AS created_at,
                to_char(updated_at, '$DATETIME_FORMAT_POSTGRES') AS updated_at 
                FROM products WHERE id = ?"""
        const val INSERT_QUERY = """INSERT INTO products
                (name, description, start_date, end_date,
                days_range, price, tax, is_active)
                VALUES (?,?,?,?,?,?,?,?)"""
        const val UPDATE_QUERY =
            """UPDATE products SET
                name = ?, description = ?, start_date = ?, end_date = ?, 
                days_range = ?, price = ?, tax = ?, is_active = ?
                WHERE id = ?"""
        const val DELETE_QUERY = "DELETE FROM products WHERE id = ?"

    }

}