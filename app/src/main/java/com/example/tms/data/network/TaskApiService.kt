package com.example.tms.data.network

import com.example.tms.data.model.Task
import com.example.tms.data.enums.TaskStatus
import retrofit2.http.*

interface TaskApiService {

    @POST("api/tasks/add")
    suspend fun addTask(@Body task: Task): Task

    @GET("api/tasks/all")
    suspend fun getAllTasks(): List<Task>

    @GET("api/tasks/get/{status}")
    suspend fun getTasksByStatus(@Path("status") status: TaskStatus): List<Task>

    @PUT("api/tasks/update/{id}")
    suspend fun updateTask(@Path("id") id: Long, @Body task: Task): Task

    @GET("api/tasks/count")
    suspend fun getCountOfTasks(): Int

    @GET("api/tasks/count/{status}")
    suspend fun getCountOfTasksByStatus(@Path("status") status: TaskStatus): Int

    @DELETE("api/tasks/delete/{id}")
    suspend fun deleteTask(@Path("id") id: Long): Task
}
