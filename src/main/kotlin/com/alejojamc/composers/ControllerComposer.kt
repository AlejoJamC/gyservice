package com.alejojamc.composers

import com.alejojamc.controllers.HealthController
import com.alejojamc.controllers.ProductController
import com.typesafe.config.Config
import io.ktor.routing.Route

class ControllerComposer(
    config: Config,
    route: Route,
    serviceComposer: ServiceComposer,
) {

    val healthController = HealthController(config, route)
    val productController = ProductController(route, serviceComposer.productService)

}