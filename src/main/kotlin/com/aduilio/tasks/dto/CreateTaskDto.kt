package com.aduilio.tasks.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import java.time.LocalDate
import java.time.LocalTime

/**
 * Represents the request to create a topic.
 */
data class CreateTaskDto(
    @field:NotBlank (message = "The title is required")
    val title: String?,
    val description: String?,
    val date: LocalDate?,
    val time: LocalTime?,
)
