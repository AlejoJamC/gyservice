package com.alejojamc.services

import com.alejojamc.entities.Product
import com.alejojamc.repositories.ProductRepository
import com.alejojamc.utils.loggerFor


class ProductService(
    private val productRepository: ProductRepository,
) {

    private val logger by lazy { loggerFor<ProductService>() }

    suspend fun getProducts(): List<Product> {
        logger.debug("On getProducts.")

        return productRepository.getProducts()
    }

    suspend fun getProductById(productId: Long): Product? {
        logger.debug("On getProductById [productId=${productId}]")

        return productRepository.getProductById(productId)
    }

    suspend fun insertProduct(Product: Product) {
        logger.debug("On insertProduct [${Product}]")

        return productRepository.insertProduct(Product)
    }

    suspend fun updateProduct(Product: Product) {
        logger.debug("On updateProduct [${Product}]")

        return productRepository.updateProduct(Product)
    }

    suspend fun deleteProduct(ProductId: Long) {
        logger.debug("On deleteProduct [ProductId=${ProductId}]")

        return productRepository.deleteProduct(ProductId)
    }

}