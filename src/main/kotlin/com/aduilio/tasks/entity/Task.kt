package com.aduilio.tasks.entity

import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalTime

/**
 * Represents a task.
 */
@Entity
@Table(name = "tasks")
data class Task(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var title: String = "",
    var description: String? = "",
    val date: LocalDate? = null,
    val time: LocalTime? = null,
    val completed: Boolean = false
)
