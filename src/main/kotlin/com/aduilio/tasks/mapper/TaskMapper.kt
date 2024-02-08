package com.aduilio.tasks.mapper

import com.aduilio.tasks.dto.CreateTaskDto
import com.aduilio.tasks.dto.ReadTaskDto
import com.aduilio.tasks.dto.UpdateTaskDto
import com.aduilio.tasks.entity.Task
import org.springframework.stereotype.Component

/**
 * Maps the attributes from/to Task DTOs.
 */
@Component
class TaskMapper {

    /**
     * Maps a CreateTaskDto to Task.
     *
     * @param createTaskDto to be mapped
     * @return Task
     */
    fun mapTaskFrom(createTaskDto: CreateTaskDto): Task {
        return Task(
            title = createTaskDto.title!!,
            description = createTaskDto.description,
            date = createTaskDto.date,
            time = createTaskDto.time,
        )
    }

    /**
     * Maps a UpdateTaskDto to Task.
     *
     * @param updateTaskDto to be mapped
     * @param currentTask with the current values
     * @return Task
     */
    fun mapTaskFrom(updateTaskDto: UpdateTaskDto, currentTask: Task): Task {
        return with(updateTaskDto) {
            Task(
                id = currentTask.id,
                title = title ?: currentTask.title,
                description = description ?: currentTask.description,
                date = date ?: currentTask.date,
                time = time ?: currentTask.time
            )
        }
    }

    /**
     * Maps a Task to ReadTaskDto.
     *
     * @param task to be mapped
     * @return ReadTaskDto
     */
    fun mapReadTaskDtoFrom(task: Task): ReadTaskDto {
        return ReadTaskDto(
            id = task.id!!,
            title = task.title,
            description = task.description,
            date = task.date,
            time = task.time,
            completed = task.completed
        )
    }
}