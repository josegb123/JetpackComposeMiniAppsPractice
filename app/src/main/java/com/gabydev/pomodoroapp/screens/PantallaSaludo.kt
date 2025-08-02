package com.gabydev.pomodoroapp.screens

import java.net.URLDecoder
import java.nio.charset.StandardCharsets
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier


@Composable
fun PantallaSaludo(nombre: String) {
    // Decodificar la cadena recibida de forma segura
    val nombreDecodificado = URLDecoder.decode(nombre, StandardCharsets.UTF_8.toString())

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "hola $nombreDecodificado",
            style = MaterialTheme.typography.headlineLarge
        )
    }
}