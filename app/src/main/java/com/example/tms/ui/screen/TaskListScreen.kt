package com.example.tms.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
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
    var selectedStatus by remember { mutableStateOf(TaskStatus.TODO) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Task Management", style = MaterialTheme.typography.displayMedium)
        Spacer(modifier = Modifier.height(8.dp))

        Row {
            Button(onClick = { selectedStatus = TaskStatus.TODO; viewModel.fetchTasksByStatus(TaskStatus.TODO) }) {
                Text("Pending")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { selectedStatus = TaskStatus.COMPLETED; viewModel.fetchTasksByStatus(TaskStatus.COMPLETED) }) {
                Text("Completed")
            }
        }

        Text("Pending Tasks: $pendingTaskCount", style = MaterialTheme.typography.displaySmall)
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn {
            items(tasks) { task ->
                TaskItem(task, viewModel::updateTask, viewModel::deleteTask)
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
                Button(onClick = { onUpdate(task.id, task.copy(taskStatus = TaskStatus.COMPLETED)) }) {
                    Text("Mark Complete")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = { onDelete(task.id) }) {
                    Text("Delete")
                }
            }
        }
    }
}
