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

    private val _completedTaskCount = MutableStateFlow(0)
    val completedTaskCount: StateFlow<Int> = _completedTaskCount

    init {
        fetchTasksByStatus(TaskStatus.PENDING) // Load only pending tasks initially
        fetchPendingTaskCount()
        fetchCompletedTaskCount()
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
            fetchCompletedTaskCount()
        }
    }

    fun updateTask(id: Long, task: Task) {
        viewModelScope.launch {
            repository.updateTask(id, task)
            fetchTasksByStatus(task.taskStatus) // ✅ Only refreshes the relevant list
            fetchPendingTaskCount()
            fetchCompletedTaskCount()
        }
    }


    fun deleteTask(id: Long) {
        viewModelScope.launch {
            repository.deleteTask(id)
            fetchAllTasks()
            fetchPendingTaskCount()
            fetchCompletedTaskCount()
        }
    }

    private fun fetchPendingTaskCount() {
        viewModelScope.launch {
            _pendingTaskCount.value = repository.getCountOfTasksByStatus(TaskStatus.PENDING)
        }
    }

    private fun fetchCompletedTaskCount() {
        viewModelScope.launch {
            _completedTaskCount.value = repository.getCountOfTasksByStatus(TaskStatus.COMPLETED)
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
