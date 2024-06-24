package com.aduilio.tasks.controller

import com.aduilio.tasks.dto.CreateTaskDto
import com.aduilio.tasks.dto.ReadTaskDto
import com.aduilio.tasks.dto.UpdateTaskDto
import com.aduilio.tasks.service.TaskService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder

/**
 * Receives the request for the resource /tasks.
 */
@RestController
@RequestMapping("/tasks")
class TaskController(private val taskService: TaskService) {

    companion object {
        const val CACHE_TASKS_KEY = "tasks"
    }

    @Operation(summary = "Creates a task")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "Task created successfully")
        ]
    )
    @PostMapping
    @Transactional
    @CacheEvict(value = [CACHE_TASKS_KEY], allEntries = true)
    fun create(
        @Valid @RequestBody createTaskDto: CreateTaskDto,
        componentsBuilder: UriComponentsBuilder
    ): ResponseEntity<ReadTaskDto> {
        val task = taskService.create(createTaskDto)
        val uri = componentsBuilder.path("/tasks/${task.id}").build().toUri()

        return ResponseEntity.created(uri).body(task)
    }

    @Operation(summary = "Updates a task")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Task updated successfully"),
            ApiResponse(responseCode = "404", description = "Task not found")
        ]
    )
    @PatchMapping("/{id}")
    @Transactional
    @CacheEvict(value = [CACHE_TASKS_KEY], allEntries = true)
    fun update(@PathVariable id: Long, @RequestBody @Valid updateTaskDto: UpdateTaskDto): ResponseEntity<ReadTaskDto> {
        val task = taskService.update(id, updateTaskDto)

        return ResponseEntity.ok(task)
    }

    @Operation(summary = "Returns all tasks")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Tasks read successfully")
        ]
    )
    @GetMapping
    @Cacheable(CACHE_TASKS_KEY)
    fun list(): ResponseEntity<List<ReadTaskDto>> {
        val topics = taskService.list()

        return ResponseEntity.ok(topics)
    }

    @Operation(summary = "Returns a task details")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Task read successfully"),
            ApiResponse(responseCode = "404", description = "Task not found")
        ]
    )
    @GetMapping("/{id}")
    fun read(@PathVariable id: Long): ResponseEntity<ReadTaskDto> {
        val topic = taskService.read(id)

        return ResponseEntity.ok(topic)
    }

    @Operation(summary = "Deletes a task")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "204", description = "Task deleted successfully"),
            ApiResponse(responseCode = "404", description = "Task not found")
        ]
    )
    @DeleteMapping("/{id}")
    @Transactional
    @CacheEvict(value = [CACHE_TASKS_KEY], allEntries = true)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Long) {
        taskService.delete(id)
    }

    @Operation(summary = "Sets a task as completed")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Task updated successfully"),
            ApiResponse(responseCode = "404", description = "Task not found")
        ]
    )
    @PostMapping("/{id}/completed")
    @Transactional
    @CacheEvict(value = [CACHE_TASKS_KEY], allEntries = true)
    fun complete(@PathVariable id: Long): ResponseEntity<ReadTaskDto> {
        val task = taskService.complete(id)

        return ResponseEntity.ok(task)
    }

    @Operation(summary = "Deletes all completed tasks")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "204", description = "All completed tasks deleted successfully")
        ]
    )
    @DeleteMapping("/completed")
    @Transactional
    @CacheEvict(value = [CACHE_TASKS_KEY], allEntries = true)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteCompletedTasks() {
        taskService.deleteCompletedTasks()
    }
}