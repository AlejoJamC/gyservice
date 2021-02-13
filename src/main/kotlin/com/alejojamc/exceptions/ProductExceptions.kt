package com.alejojamc.exceptions

import com.alejojamc.entities.ApplicationError
import io.ktor.http.HttpStatusCode


sealed class ProductExceptions(
    override var httpStatus: HttpStatusCode,
    override var code: String,
    override var args: List<String>? = null,
) : ApplicationError(httpStatus, code, args) {

    class SelectProductsError : ProductExceptions(
        httpStatus = HttpStatusCode.InternalServerError,
        code = "error.repository.product.list"
    )

    class SelectProductByIdError(productId: String) : ProductExceptions(
        httpStatus = HttpStatusCode.InternalServerError,
        code = "error.repository.product.by.id",
        args = listOf(productId)
    )

    class InsertError(productName: String) : ProductExceptions(
        httpStatus = HttpStatusCode.InternalServerError,
        code = "error.repository.product.insert",
        args = listOf(productName)
    )

    class UpdateError(productId: String) : ProductExceptions(
        httpStatus = HttpStatusCode.InternalServerError,
        code = "error.repository.product.update",
        args = listOf(productId)
    )

    class DeleteError(productId: String) : ProductExceptions(
        httpStatus = HttpStatusCode.InternalServerError,
        code = "error.repository.product.delete",
        args = listOf(productId)
    )

}