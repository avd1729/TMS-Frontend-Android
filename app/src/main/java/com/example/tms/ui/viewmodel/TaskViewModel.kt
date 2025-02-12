package com.example.tms.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tms.data.model.Task
import com.example.tms.data.enums.TaskStatus
import com.example.tms.data.repository.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TaskViewModel(private val repository: TaskRepository) : ViewModel() {

    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks

    private val _pendingTaskCount = MutableStateFlow(0)
    val pendingTaskCount: StateFlow<Int> = _pendingTaskCount

    init {
        fetchAllTasks()
        fetchPendingTaskCount()
    }

    fun fetchAllTasks() {
        viewModelScope.launch {
            _tasks.value = repository.getAllTasks()
        }
    }

    private val _taskCount = MutableStateFlow(0)
    val taskCount: StateFlow<Int> = _taskCount

    fun fetchTaskCountByStatus(status: TaskStatus) {
        viewModelScope.launch {
            _taskCount.value = repository.getCountOfTasksByStatus(status)
        }
    }


    fun addTask(task: Task) {
        viewModelScope.launch {
            repository.addTask(task)
            fetchAllTasks()
            fetchPendingTaskCount()
        }
    }

    fun updateTask(id: Long, task: Task) {
        viewModelScope.launch {
            repository.updateTask(id, task)
            fetchAllTasks()
            fetchPendingTaskCount()
        }
    }

    fun deleteTask(id: Long) {
        viewModelScope.launch {
            repository.deleteTask(id)
            fetchAllTasks()
            fetchPendingTaskCount()
        }
    }

    private fun fetchPendingTaskCount() {
        viewModelScope.launch {
            _pendingTaskCount.value = repository.getCountOfTasksByStatus(TaskStatus.PENDING)
        }
    }

    fun getTaskCountByStatus(selectedStatus: TaskStatus) {
        viewModelScope.launch {
            _pendingTaskCount.value = repository.getCountOfTasksByStatus(selectedStatus)
        }
    }

    fun fetchTasksByStatus(status: TaskStatus) {
        viewModelScope.launch {
            _tasks.value = repository.getTasksByStatus(status)
        }

    }
}
