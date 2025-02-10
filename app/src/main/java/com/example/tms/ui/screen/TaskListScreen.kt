package com.example.tms.ui.screen

import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tms.data.model.Task
import com.example.tms.viewmodel.TaskViewModel

@Composable
fun TaskListScreen(viewModel: TaskViewModel = viewModel(), onAddTaskClick: () -> Unit) {
    val tasks by viewModel.tasks.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchAllTasks() // Load tasks when screen opens
    }

    Scaffold(
        topBar = { TaskTopBar() },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddTaskClick) {
                Text("+")
            }
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            if (tasks.isEmpty()) {
                EmptyTaskList()
            } else {
                TaskList(tasks)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskTopBar() {
    TopAppBar(title = { Text("Task Manager") })
}

@Composable
fun EmptyTaskList() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
        Text("No tasks available", style = MaterialTheme.typography.displaySmall)
    }
}

@Composable
fun TaskList(tasks: List<Task>) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(tasks) { task ->
            TaskItem(task)
        }
    }
}

@Composable
fun TaskItem(task: Task) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { /* Handle click event */ },
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Title: ${task.title}", style = MaterialTheme.typography.displaySmall)
            Text(text = "Description: ${task.description}")
            Text(text = "Status: ${task.taskStatus}")
        }
    }
}
