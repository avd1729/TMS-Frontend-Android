package com.example.tms.data.repository

import com.example.tms.data.model.Task
import com.example.tms.data.enums.TaskStatus
import com.example.tms.data.network.ApiClient

class TaskRepository {
    private val api = ApiClient.apiService

    suspend fun addTask(task: Task) = api.addTask(task)
    suspend fun getAllTasks() = api.getAllTasks()
    suspend fun getTasksByStatus(status: TaskStatus) = api.getTasksByStatus(status)
    suspend fun updateTask(id: Long, task: Task) = api.updateTask(id, task)
    suspend fun getCountOfTasks() = api.getCountOfTasks()
    suspend fun getCountOfTasksByStatus(status: TaskStatus) = api.getCountOfTasksByStatus(status)
    suspend fun deleteTask(id: Long) = api.deleteTask(id)
}
