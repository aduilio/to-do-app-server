package com.aduilio.tasks.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import java.time.LocalDate
import java.time.LocalTime

/**
 * Represents the request to create a topic.
 */
@Schema(description = "Model to create a task")
data class CreateTaskDto(
    @field:NotBlank (message = "The title is required")
    val title: String?,
    val description: String?,
    val date: LocalDate?,
    val time: LocalTime?,
)
