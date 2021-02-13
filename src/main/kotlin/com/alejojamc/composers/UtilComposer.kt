package com.alejojamc.composers

import com.alejojamc.entities.Gyservice
import com.alejojamc.utils.GYSERVICE
import com.alejojamc.utils.Messages
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.typesafe.config.Config
import io.github.config4k.extract
import java.util.Locale

class UtilComposer(config: Config) {
    private val locale: Locale
    val messages: Messages

    val objectMapper: ObjectMapper = ObjectMapper().apply {
        registerModule(Jdk8Module())
        registerModule(JavaTimeModule())
        registerKotlinModule()
        configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    }

    init {
        val gySettings: Gyservice = config.extract(GYSERVICE)
        locale = Locale(gySettings.locale, gySettings.country)
        messages = Messages(locale)
    }

}