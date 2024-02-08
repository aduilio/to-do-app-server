package com.aduilio.tasks.dto

import java.time.LocalDate
import java.time.LocalTime

/**
 * Represents the request to update a topic.
 */
data class UpdateTaskDto(
    val title: String?,
    val description: String?,
    val date: LocalDate?,
    val time: LocalTime?,
)

