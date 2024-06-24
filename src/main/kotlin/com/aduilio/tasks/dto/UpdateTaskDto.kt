package com.aduilio.tasks.dto

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDate
import java.time.LocalTime

/**
 * Represents the request to update a topic.
 */
@Schema(description = "Model to update a task")
data class UpdateTaskDto(
    val title: String?,
    val description: String?,
    val date: LocalDate?,
    val time: LocalTime?,
)

