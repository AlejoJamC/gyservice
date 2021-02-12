package com.alejojamc.config

import com.typesafe.config.Config
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.features.json.JacksonSerializer
import io.ktor.client.features.json.JsonFeature

class HttpClientConfig(config: Config) {

    fun getHttpClient(): HttpClient {
        return HttpClient(OkHttp) {
            install(JsonFeature) {
                serializer = JacksonSerializer()
            }
        }
    }

}
