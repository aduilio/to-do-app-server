package com.aduilio.tasks.config

import io.swagger.v3.oas.models.media.Schema
import org.springdoc.core.utils.SpringDocUtils
import org.springframework.context.annotation.Configuration
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Configuration
class SwaggerConfig {

    init {
        val schemaDate: Schema<LocalDate> = Schema()
        schemaDate.example(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
        SpringDocUtils.getConfig().replaceWithSchema(LocalDate::class.java, schemaDate)

        val schemaTime: Schema<LocalTime> = Schema()
        schemaTime.example(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")))
        SpringDocUtils.getConfig().replaceWithSchema(LocalTime::class.java, schemaTime)
    }
}