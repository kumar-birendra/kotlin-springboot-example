package com.guidewire.taskmanager.controller

import com.guidewire.taskmanager.dto.TaskRequestDTO
import com.guidewire.taskmanager.dto.TaskResponseDTO
import com.guidewire.taskmanager.service.TaskService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/tasks")
class TaskController (var taskService: TaskService) {

    @PostMapping("/create")
    fun createTask(@RequestBody task: TaskRequestDTO) : TaskResponseDTO {
        return taskService.createTask(task)
    }

    @GetMapping("/{id}")
    fun getTaskById(@PathVariable id: Long) : TaskResponseDTO {
        return taskService.getTask(id)
    }

    @PutMapping("/{id}")
    fun updateTask(@PathVariable id: Long, @RequestBody task: TaskRequestDTO) : TaskResponseDTO {
        return taskService.updateTask(id, task) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found")
    }

    @DeleteMapping("/{id}")
    fun deleteTask(@PathVariable id: Long) {
        taskService.deleteTask(id)
    }
}