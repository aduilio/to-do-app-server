package com.aduilio.tasks.repository

import com.aduilio.tasks.entity.Task
import org.springframework.data.jpa.repository.JpaRepository

/**
 * Provides the access to the database table tasks.
 */
interface TaskRepository : JpaRepository<Task, Long> {

    fun deleteByCompleted(completed: Boolean = true)

}