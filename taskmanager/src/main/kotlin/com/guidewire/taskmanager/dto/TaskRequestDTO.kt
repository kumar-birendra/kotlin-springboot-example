package com.guidewire.taskmanager.dto

import com.fasterxml.jackson.annotation.JsonProperty

class TaskRequestDTO (@JsonProperty("name") var name: String, @JsonProperty("description") var description: String, @JsonProperty("done") var done: Boolean) {
}