package com.gabydev.pomodoroapp.features.tareas.models

data class Tarea(
    val id: Int,
    val titulo: String,
    val isCompletada: Boolean = false,
    val isExpandida: Boolean = false
)
