package com.example.tms.data.model

import com.example.tms.data.enums.TaskStatus

data class Task (
    val id: Long = 0,
    var title: String?= null,
    var description: String? = null,
    var taskStatus: TaskStatus = TaskStatus.PENDING,
)