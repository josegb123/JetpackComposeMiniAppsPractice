package com.gabydev.pomodoroapp.ui.navigation


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun PantallaPrincipal(navController: NavController) {
    val brush = remember {
        Brush.linearGradient(
            colors = listOf(Color.Red, Color.Yellow, Color.Green, Color.Blue, Color.Magenta)
        )
    }

    var stateText by remember { mutableStateOf("") }

    val encodedText = URLEncoder.encode(stateText, StandardCharsets.UTF_8.toString())

    Column(modifier = Modifier.padding(16.dp).fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Pantalla Principal",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(Modifier.height(16.dp))

        Button(onClick = { navController.navigate("pomodoro_screen") }) {
            Text("Ir a Pomodoro")
        }

        Spacer(Modifier.height(43.dp))

        OutlinedTextField(
            value = stateText,
            onValueChange = { stateText = it },
            textStyle = TextStyle(brush = brush),
            placeholder = { Text("Escribe algo...") }
        )

        Spacer(Modifier.height(16.dp))

        Button(onClick = { navController.navigate("pantalla_saludo/${encodedText}") }) {
            Text("Ir a saludoApp")
        }

    }
}

@Preview(showSystemUi = true)
@Composable
fun PantallaPrincipalPreview(){
    PantallaPrincipal(navController = NavController(
        LocalContext.current))
}