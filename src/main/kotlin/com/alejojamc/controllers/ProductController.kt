package com.alejojamc.controllers

import com.alejojamc.dto.ProductDTO
import com.alejojamc.entities.Product
import com.typesafe.config.Config
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.*

class ProductController(
    config: Config,
    route: Route,
) {

    init {
        route.route("/products") {
            post {
                val brand = call.receive<ProductDTO>()
                call.respond(HttpStatusCode.Created, insertProduct(brand))
            }
            put {
                val brand = call.receive<ProductDTO>()
                updateProduct(brand)
                call.respond(HttpStatusCode.NoContent)
            }
            route("/{productId}") {
                get {
                    val brandId = call.parameters[PRODUCT_ID_PARAM]
                    getProduct(brandId)?.let { it1 -> call.respond(it1) }
                }
                delete {
                    val brandId = call.parameters[PRODUCT_ID_PARAM]
                    deleteProduct(brandId)
                    call.respond(HttpStatusCode.NoContent)
                }
            }
        }
    }

    private suspend fun getProduct(productId: String?): Product? {
        // add service
        // add exception
        return null
    }

    private suspend fun insertProduct(product: ProductDTO) {
        // Add service
    }

    private suspend fun updateProduct(product: ProductDTO) {
        // Add service
    }

    private suspend fun deleteProduct(productId: String?) {
        // Add service
    }

    companion object {
        const val PRODUCT_ID_PARAM = "productId"
    }

}