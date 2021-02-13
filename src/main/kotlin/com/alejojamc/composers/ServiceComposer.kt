package com.alejojamc.composers

import com.alejojamc.services.ProductService
import com.typesafe.config.Config

class ServiceComposer(
    config: Config,
    repositoryComposer: RepositoryComposer,
) {

    val productService = ProductService(repositoryComposer.productRepository)

}