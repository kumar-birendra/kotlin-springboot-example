package com.guidewire.taskmanager.rest

import com.guidewire.taskmanager.TaskmanagerApplication
import com.guidewire.taskmanager.dto.TaskRequestDTO
import com.guidewire.taskmanager.dto.TaskResponseDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import kotlin.test.Test
import kotlin.test.assertTrue

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = [TaskmanagerApplication::class])
class TaskCRUDIntegrationTest(@Autowired var restTemplate: TestRestTemplate) {

    var taskId: Long? = null

    @Test
    fun createTask(){
        val taskRequestDTO = TaskRequestDTO(name="Task", description = "description", done = false)
        val result = this.restTemplate.postForEntity("/api/tasks/create", taskRequestDTO, TaskResponseDTO::class.java)
        taskId = result.body?.id!! //dubble bang operator to make sure this is not null . i.e. if id is null then throw NPE
        assertTrue { result.body?.name.equals("Task") }
        assertTrue { result.body?.description.equals("description") }
    }


    @Test
    fun updateTask(){
        createTask()
        val taskRequestDTO = TaskRequestDTO(name="Task", description = "description", done = true)
        this.restTemplate.put("/api/tasks/{id}", taskRequestDTO, taskId)

        val getTask = this.restTemplate.getForEntity("/api/tasks/{id}",  TaskResponseDTO::class.java, taskId)
        assertTrue(getTask.statusCode.is2xxSuccessful)
        assertTrue(getTask.body?.done!!)
    }

    @Test
    fun deleteTask(){
        createTask()
        this.restTemplate.delete("/api/tasks/{id}", taskId)
        var result  = this.restTemplate.getForEntity("/api/tasks/{id}",  String::class.java, taskId)
        assertTrue { result.statusCode.equals(HttpStatus.NOT_FOUND)}
    }
}