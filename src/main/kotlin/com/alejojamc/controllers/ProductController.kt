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
            post {
                val brand = call.receive<Product>()
                call.respond(HttpStatusCode.Created, insertProduct(brand))
            }
            put {
                val brand = call.receive<Product>()
                updateProduct(brand)
                call.respond(HttpStatusCode.NoContent)
            }
            route("/{productId}") {
                get {
                    val brandId = call.parameters[PRODUCT_ID_PARAM]
                    getProductById(brandId)?.let { it1 -> call.respond(it1) }
                }
                delete {
                    val brandId = call.parameters[PRODUCT_ID_PARAM]
                    deleteProduct(brandId)
                    call.respond(HttpStatusCode.NoContent)
                }
            }
        }
    }

    private suspend fun getProducts(): List<Product> {
        return productService.getProducts()
    }

    private suspend fun getProductById(productId: String?): Product? {
        return productId?.let {
            productService.getProductById(it.toLong())
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
        const val PRODUCT_ID_PARAM = "productId"
    }

}