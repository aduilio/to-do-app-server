package com.aduilio.tasks.controller

import com.aduilio.tasks.dto.CreateTaskDto
import com.aduilio.tasks.dto.ReadTaskDto
import com.aduilio.tasks.dto.UpdateTaskDto
import com.aduilio.tasks.service.TaskService
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

    @PostMapping
    @Transactional
    @CacheEvict(value = ["tasks"], allEntries = true)
    fun create(
        @Valid @RequestBody createTaskDto: CreateTaskDto,
        componentsBuilder: UriComponentsBuilder
    ): ResponseEntity<ReadTaskDto> {
        val task = taskService.create(createTaskDto)
        val uri = componentsBuilder.path("/tasks/${task.id}").build().toUri()

        return ResponseEntity.created(uri).body(task)
    }

    @PatchMapping("/{id}")
    @Transactional
    @CacheEvict(value = ["tasks"], allEntries = true)
    fun update(@PathVariable id: Long, @RequestBody @Valid updateTaskDto: UpdateTaskDto): ResponseEntity<ReadTaskDto> {
        val task = taskService.update(id, updateTaskDto)

        return ResponseEntity.ok(task)
    }

    @GetMapping
    @Cacheable("tasks")
    fun list(): ResponseEntity<List<ReadTaskDto>> {
        val topics = taskService.list()

        return ResponseEntity.ok(topics)
    }

    @GetMapping("/{id}")
    fun read(@PathVariable id: Long): ResponseEntity<ReadTaskDto> {
        val topic = taskService.read(id)

        return ResponseEntity.ok(topic)
    }

    @DeleteMapping("/{id}")
    @CacheEvict(value = ["tasks"], allEntries = true)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Long) {
        taskService.delete(id)
    }

    @PostMapping("/{id}/completed")
    @Transactional
    @CacheEvict(value = ["tasks"], allEntries = true)
    fun complete(@PathVariable id: Long): ResponseEntity<ReadTaskDto> {
        val task = taskService.complete(id)

        return ResponseEntity.ok(task)
    }
}