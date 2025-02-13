package com.example.tms.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tms.data.model.Task
import com.example.tms.data.enums.TaskStatus
import com.example.tms.ui.viewmodel.TaskViewModel

@Composable
fun TaskScreen(viewModel: TaskViewModel = viewModel()) {
    val tasks by viewModel.tasks.collectAsState()
    val pendingTaskCount by viewModel.pendingTaskCount.collectAsState()
    val completedTaskCount by viewModel.completedTaskCount.collectAsState()
    var selectedStatus by remember { mutableStateOf(TaskStatus.PENDING) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Spacer(modifier = Modifier.height(8.dp))
        Text("Task Management System", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(8.dp))

        Row {
            Button(
                onClick = {
                    selectedStatus = TaskStatus.PENDING
                    viewModel.fetchTasksByStatus(TaskStatus.PENDING)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedStatus == TaskStatus.PENDING) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primary
                )
            ) {
                Text("Pending : $pendingTaskCount")
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = {
                    selectedStatus = TaskStatus.COMPLETED
                    viewModel.fetchTasksByStatus(TaskStatus.COMPLETED)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedStatus == TaskStatus.COMPLETED) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primary
                )
            ) {
                Text("Completed : $completedTaskCount")
            }
        }


        LazyColumn {
            items(tasks) { task ->
                TaskItem(task, onUpdate = { id, updatedTask ->
                    viewModel.updateTask(id, updatedTask)

                    // Update selectedStatus when switching between task states
                    selectedStatus = updatedTask.taskStatus

                    viewModel.fetchTasksByStatus(selectedStatus)
                }, onDelete = { id ->
                    viewModel.deleteTask(id)
                    viewModel.fetchTasksByStatus(selectedStatus)
                })
            }
        }
    }
}

@Composable
fun TaskItem(task: Task, onUpdate: (Long, Task) -> Unit, onDelete: (Long) -> Unit) {
    Card(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("${task.title}", style = MaterialTheme.typography.displaySmall)
            Text("Status: ${task.taskStatus}")
            Row {
                if (task.taskStatus == TaskStatus.PENDING) {
                    Button(onClick = { onUpdate(task.id, task.copy(taskStatus = TaskStatus.COMPLETED)) }) {
                        Text("Mark Complete")
                    }
                } else {
                    Button(onClick = { onUpdate(task.id, task.copy(taskStatus = TaskStatus.PENDING)) }) {
                        Text("Mark Pending")
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = { onDelete(task.id) }) {
                    Text("Delete")
                }
            }
        }
    }
}
