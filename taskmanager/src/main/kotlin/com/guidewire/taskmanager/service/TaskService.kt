package com.guidewire.taskmanager.service

import com.guidewire.taskmanager.dao.TaskRepository
import com.guidewire.taskmanager.dto.TaskRequestDTO
import com.guidewire.taskmanager.dto.TaskResponseDTO
import com.guidewire.taskmanager.model.TaskEntity
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import kotlin.jvm.optionals.getOrNull

@Service
class TaskService (var taskRepository: TaskRepository) {
    fun createTask(task: TaskRequestDTO) : TaskResponseDTO {
        val savedTask = taskRepository.save(TaskEntity(id=null, title = task.name, description = task.description, done =task.done))
        return TaskResponseDTO(id = savedTask.id!!, name = task.name, description = task.description, done = savedTask.done)
    }

    fun getTask(id: Long): TaskResponseDTO {
        return getTaskById(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found")
    }

    fun getTaskById(id: Long): TaskResponseDTO? {
        return taskRepository.findById(id).map { TaskResponseDTO(id = it.id!!, name=it.title, description = it.description, done = it.done) }.getOrNull()
    }

    fun updateTask(id: Long, updatedTask: TaskRequestDTO): TaskResponseDTO? {
        return taskRepository.findById(id).map {
            val save = taskRepository.save(TaskEntity(id=it.id, title =updatedTask.name, description = updatedTask.description, done=updatedTask.done))
            TaskResponseDTO(id=save.id!!, name = save.title, description = save.description, done=save.done)
        }.orElseGet(null)
    }

    fun deleteTask(id: Long) {
        taskRepository.deleteById(id)
    }
}
