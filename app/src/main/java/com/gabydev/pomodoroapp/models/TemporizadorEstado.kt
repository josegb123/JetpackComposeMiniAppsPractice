package com.gabydev.pomodoroapp.models

data class TemporizadorEstado(
    val tiempoRestante: Long,
    val estaCorriendo: Boolean = false
)