package com.gabydev.pomodoroapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gabydev.pomodoroapp.R
import com.gabydev.pomodoroapp.viewModels.PomodoroViewModel


/**
 * Función componible que muestra la pantalla del temporizador Pomodoro.
 *
 * Esta pantalla muestra el tiempo restante y botones para iniciar y reiniciar el temporizador.
 * Observa el estado del temporizador desde el [PomodoroViewModel].
 *
 * @param viewModel La instancia de [PomodoroViewModel] que gestiona la lógica del temporizador Pomodoro.
 *                  Por defecto, se crea una nueva instancia de ViewModel si no se proporciona.
 */
@Preview(showSystemUi = true)
@Composable
fun PomodoroScreen(viewModel: PomodoroViewModel = viewModel()) {
    // Observamos el StateFlow del ViewModel
    val estado by viewModel.estadoTemporizador.collectAsState()

    Column(
        modifier = Modifier
            .background(colorResource(R.color.orange))
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,

        ) {
        Card {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)
            ) {
                // Formateamos y mostramos el tiempo
                with(estado) {
                    val minutos = tiempoRestante / 1000 / 60
                    val segundos = tiempoRestante / 1000 % 60
                    Text(
                        text = String.format("%02d:%02d", minutos, segundos),
                        style = MaterialTheme.typography.headlineLarge
                    )
                }


                Spacer(modifier = Modifier.height(32.dp))

                // Botón de iniciar, con lógica condicional
                Button(
                    onClick = { viewModel.iniciarTemporizador() },
                    enabled = !estado.estaCorriendo // Desactivado si el temporizador está corriendo
                ) {
                    Text("Iniciar")
                }

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = { viewModel.pararTemporizador() },
                    enabled = estado.estaCorriendo // Desactivado si el temporizador no está corriendo
                ) {
                    Text("Parar")
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Botón de reiniciar
                Button(onClick = { viewModel.reiniciarTemporizador() }) {
                    Text("Reiniciar")
                }
            }
        }
    }
}