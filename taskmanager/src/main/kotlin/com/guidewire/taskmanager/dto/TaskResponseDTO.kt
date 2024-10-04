package com.guidewire.taskmanager.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class TaskResponseDTO(
    @JsonProperty("id")
    var id: Long,
    @JsonProperty("name")
    var name: String,
    @JsonProperty("description")
    var description: String,
    @JsonProperty("done")
    var done: Boolean)