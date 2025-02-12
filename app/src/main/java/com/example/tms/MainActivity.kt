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
