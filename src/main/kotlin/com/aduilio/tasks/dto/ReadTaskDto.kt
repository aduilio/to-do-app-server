package com.aduilio.tasks.dto

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDate
import java.time.LocalTime

/**
 * Represents the response to read a topic.
 */
@JsonInclude(Include.NON_NULL)
@Schema(description = "Model to read a task")
data class ReadTaskDto(
    val id: Long,
    val title: String,
    val description: String? = null,
    val date: LocalDate? = null,
    val time: LocalTime? = null,
    val completed: Boolean = false
)
