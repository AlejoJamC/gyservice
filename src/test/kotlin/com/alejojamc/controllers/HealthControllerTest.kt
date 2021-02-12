package com.alejojamc.controllers

import com.alejojamc.module
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.withTestApplication
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Test

class HealthControllerTest {

    @Test
    fun `get project health - OK`() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Get, "/api/gy/health").apply {
                MatcherAssert.assertThat(response.status(), CoreMatchers.`is`(HttpStatusCode.OK))
                MatcherAssert.assertThat(
                    response.content!!.lines().toString(),
                    CoreMatchers.`is`("""[{,   "status" : "UP",,   "version" : "local", }]""")
                )
            }
        }
    }

}