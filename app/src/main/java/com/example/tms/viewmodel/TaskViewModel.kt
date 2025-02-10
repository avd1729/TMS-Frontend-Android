package com.example.tms.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tms.data.model.Task
import com.example.tms.data.enums.TaskStatus
import com.example.tms.data.repository.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TaskViewModel : ViewModel() {
    private val repository = TaskRepository()

    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks = _tasks.asStateFlow()

    fun fetchAllTasks() {
        viewModelScope.launch {
            _tasks.value = repository.getAllTasks()
        }
    }

    fun addNewTask(task: Task) {
        viewModelScope.launch {
            repository.addTask(task)
            fetchAllTasks() // Refresh after adding
        }
    }
}
