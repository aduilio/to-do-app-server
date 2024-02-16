package com.aduilio.tasks.service

import com.aduilio.tasks.dto.CreateTaskDto
import com.aduilio.tasks.dto.ReadTaskDto
import com.aduilio.tasks.dto.UpdateTaskDto
import com.aduilio.tasks.entity.Task
import com.aduilio.tasks.exception.NotFoundException
import com.aduilio.tasks.exception.TaskCompleteException
import com.aduilio.tasks.mapper.TaskMapper
import com.aduilio.tasks.repository.TaskRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service

/**
 * Provides services related to tasks.
 */
@Service
class TaskService(
    private val taskRepository: TaskRepository,
    private val taskMapper: TaskMapper
) {

    private val logger = KotlinLogging.logger { }

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
        logger.debug { "Creating a task: $createTaskDto"}

        val task = taskMapper.mapTaskFrom(createTaskDto)
        val result = taskRepository.save(task)

        logger.debug { "Task created with id: ${result.id}"}

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
        logger.debug { "Updating the task with id: $id"}

        val task = readTask(id)
        if (task.completed) {
            logger.debug { "Update not allowed. Task is completed."}

            throw TaskCompleteException(TASK_ALREADY_COMPLETED)
        }

        val updateTask = taskMapper.mapTaskFrom(updateTaskDto, task)
        taskRepository.save(updateTask)

        logger.debug { "Task with id: $id updated"}

        return taskMapper.mapReadTaskDtoFrom(updateTask)
    }

    /**
     * Returns all tasks.
     *
     * @return List of ReadTaskDto
     */
    fun list(): List<ReadTaskDto> {
        logger.debug { "Listing all tasks"}

        val tasks = taskRepository.findAllByOrderByIdDesc()

        logger.debug { "Found ${tasks.size} tasks"}

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
        logger.debug { "Reading the task with id: $id"}

        val task = readTask(id)

        logger.debug { "Task: $task"}

        return taskMapper.mapReadTaskDtoFrom(task)
    }

    /**
     * Deletes a task.
     *
     * @param id the task id
     * @exception NotFoundException if the id is invalid
     */
    fun delete(id: Long) {
        logger.debug { "Deleting the task with id: $id"}

        taskRepository.deleteById(id)

        logger.debug { "Task with id: $id deleted"}
    }

    /**
     * Marks a task as completed.
     *
     * @param id the task id
     * @exception NotFoundException if the id is invalid
     */
    fun complete(id: Long): ReadTaskDto {
        logger.debug { "Completing the task with id: $id"}

        val task = readTask(id).copy(completed = true)
        taskRepository.save(task)

        logger.debug { "Task with id: $id completed"}

        return taskMapper.mapReadTaskDtoFrom(task)
    }

    /**
     * Deletes all completed tasks.
     */
    fun deleteCompletedTasks() {
        logger.debug { "Deleting all completed tasks"}

        taskRepository.deleteByCompleted()

        logger.debug { "All completed tasks deleted"}
    }

    private fun readTask(id: Long): Task {
        return taskRepository.findById(id)
            .orElseThrow { NotFoundException(NOT_FOUND_MESSAGE) }
    }
}