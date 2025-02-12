package com.example.tms

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tms.ui.screen.TaskScreen
import com.example.tms.ui.theme.TmsTheme
import com.example.tms.ui.viewmodel.TaskViewModel
import com.example.tms.data.repository.TaskRepository
import com.example.tms.data.enums.TaskStatus
import com.example.tms.data.model.Task

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = TaskRepository()
        val taskViewModel = TaskViewModel(repository)

        setContent {
            TmsTheme {
                Surface(color = MaterialTheme.colorScheme.primary) {
                    TaskScreen(viewModel = taskViewModel)
                }
            }
        }
    }
}

@Composable
fun TaskScreen(viewModel: TaskViewModel) {
    var selectedStatus by remember { mutableStateOf(TaskStatus.PENDING) }
    var showAddDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Task Management", style = MaterialTheme.typography.displayMedium)
        Spacer(modifier = Modifier.height(16.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            TaskStatus.values().forEach { status ->
                Button(onClick = {
                    selectedStatus = status
                    viewModel.fetchTasksByStatus(status)
                }) {
                    Text(status.name.replace("_", " "))
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text("${selectedStatus.name.replace("_", " ")} Tasks: ${viewModel.getTaskCountByStatus(selectedStatus)}")

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { showAddDialog = true }) {
            Text("Add New Task")
        }

        if (showAddDialog) {
            AddTaskDialog(onAddTask = { title ->
                viewModel.addTask(Task(title = title, taskStatus = TaskStatus.PENDING))
            }, onDismiss = { showAddDialog = false })
        }
    }
}

@Composable
fun AddTaskDialog(onAddTask: (String) -> Unit, onDismiss: () -> Unit) {
    var taskTitle by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add New Task") },
        text = {
            Column {
                Text("Enter task title:")
                BasicTextField(
                    value = taskTitle,
                    onValueChange = { taskTitle = it },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(onClick = {
                if (taskTitle.isNotBlank()) {
                    onAddTask(taskTitle)
                }
                onDismiss()
            }) {
                Text("Add")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
