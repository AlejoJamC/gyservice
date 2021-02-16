package com.alejojamc.controllers

import com.alejojamc.entities.Product
import com.alejojamc.exceptions.ProductExceptions
import com.alejojamc.services.ProductService
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.*


class ProductController(
    route: Route,
    private val productService: ProductService
) {

    init {

        route.route("/products") {
            get {
                call.respond(HttpStatusCode.OK, getProducts())
            }
            post {
                val product = call.receive<Product>()
                call.respond(HttpStatusCode.Created, insertProduct(product))
            }
            put {
                val product = call.receive<Product>()
                updateProduct(product)
                call.respond(HttpStatusCode.NoContent)
            }

            route("/{productId}") {
                get {
                    val productId = call.parameters[PRODUCT_ID_PARAM]
                    call.respond(HttpStatusCode.OK, getProductById(productId))
                }
                delete {
                    val productId = call.parameters[PRODUCT_ID_PARAM]
                    deleteProduct(productId)
                    call.respond(HttpStatusCode.NoContent)
                }
            }

        }

    }

    private suspend fun getProducts(): List<Product> {
        return productService.getProducts()
    }

    private suspend fun getProductById(productId: String?): Product {
        return productId?.let {
            productService.getProductById(it.toLong()) ?: throw ProductExceptions.ProductNotFound()
        } ?: throw ProductExceptions.ProductBadRequest()
    }

    private suspend fun insertProduct(product: Product) {
        return productService.insertProduct(product)
    }

    private suspend fun updateProduct(product: Product) {
        return productService.updateProduct(product)
    }

    private suspend fun deleteProduct(productId: String?) {
        return productId?.let {
            productService.deleteProduct(it.toLong())
        } ?: throw ProductExceptions.ProductBadRequest()
    }

    companion object {
        private const val PRODUCT_ID_PARAM = "productId"
    }

}