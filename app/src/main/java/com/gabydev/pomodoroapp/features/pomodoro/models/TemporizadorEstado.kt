package com.gabydev.pomodoroapp.features.pomodoro.models

data class TemporizadorEstado(
    val tiempoRestante: Long,
    val estaCorriendo: Boolean = false
)