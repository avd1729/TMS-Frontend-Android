package com.example.tms.data.model

import com.example.tms.data.enums.TaskStatus

data class Task (
    val id: Long?= null,
    var title: String?= null,
    var description: String? = null,
    var taskStatus: TaskStatus = TaskStatus.PENDING,
)