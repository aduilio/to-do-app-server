package com.aduilio.tasks.service

import com.aduilio.tasks.dto.CreateTaskDto
import com.aduilio.tasks.dto.ReadTaskDto
import com.aduilio.tasks.dto.UpdateTaskDto
import com.aduilio.tasks.entity.Task
import com.aduilio.tasks.exception.NotFoundException
import com.aduilio.tasks.exception.TaskCompleteException
import com.aduilio.tasks.mapper.TaskMapper
import com.aduilio.tasks.repository.TaskRepository
import org.springframework.stereotype.Service

/**
 * Provides services related to tasks.
 */
@Service
class TaskService(
    private val taskRepository: TaskRepository,
    private val taskMapper: TaskMapper
) {

    companion object {
        const val NOT_FOUND_MESSAGE = "Task not found"
        const val TASK_ALREADY_COMPLETED = "This task is already completed"
    }

    /**
     * Creates a task.
     *
     * @param createTaskDto the information to be created
     * @return ReadTaskDto
     */
    fun create(createTaskDto: CreateTaskDto): ReadTaskDto {
        val task = taskMapper.mapTaskFrom(createTaskDto)
        val result = taskRepository.save(task)

        return taskMapper.mapReadTaskDtoFrom(result)
    }

    /**
     * Updates a task.
     *
     * @param id the task id
     * @param updateTaskDto the information to be updated
     * @return ReadTaskDto
     * @exception NotFoundException if the id is invalid
     * @exception TaskCompleteException if the task is already completed
     */
    fun update(id: Long, updateTaskDto: UpdateTaskDto): ReadTaskDto {
        val task = readTask(id)
        if (task.completed) {
            throw TaskCompleteException(TASK_ALREADY_COMPLETED)
        }

        val updateTask = taskMapper.mapTaskFrom(updateTaskDto, task)
        taskRepository.save(updateTask)

        return taskMapper.mapReadTaskDtoFrom(updateTask)
    }

    /**
     * Returns all tasks.
     *
     * @return List of ReadTaskDto
     */
    fun list(): List<ReadTaskDto> {
        val tasks = taskRepository.findAll()

        return tasks.map { task -> taskMapper.mapReadTaskDtoFrom(task) }
    }

    /**
     * Returns the task by id
     *
     * @param id the task id
     * @return ReadTaskDto
     * @exception NotFoundException if the id is invalid
     */
    fun read(id: Long): ReadTaskDto {
        val task = readTask(id)

        return taskMapper.mapReadTaskDtoFrom(task)
    }

    /**
     * Deletes a task.
     *
     * @param id the task id
     * @exception NotFoundException if the id is invalid
     */
    fun delete(id: Long) {
        taskRepository.deleteById(id)
    }

    /**
     * Marks a task as completed.
     *
     * @param id the task id
     * @exception NotFoundException if the id is invalid
     */
    fun complete(id: Long): ReadTaskDto {
        val task = readTask(id).copy(completed = true)
        taskRepository.save(task)

        return taskMapper.mapReadTaskDtoFrom(task)
    }

    private fun readTask(id: Long): Task {
        return taskRepository.findById(id)
            .orElseThrow { NotFoundException(NOT_FOUND_MESSAGE) }
    }
}