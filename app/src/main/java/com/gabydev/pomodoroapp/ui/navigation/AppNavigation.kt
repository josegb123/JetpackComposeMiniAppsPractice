package com.gabydev.pomodoroapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gabydev.pomodoroapp.features.pomodoro.PomodoroScreen
import com.gabydev.pomodoroapp.features.saludo.PantallaSaludo
import com.gabydev.pomodoroapp.features.tareas.TareasScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "pantalla_principal") {

        composable("pantalla_principal") {
            // Las rutas deben ser nombradas igual que los composable de abajo
            PantallaPrincipal(
                onPomodoroApp = { navController.navigate("pomodoro_screen") },
                onSaludoApp = { encodedText -> navController.navigate("pantalla_saludo/$encodedText") },
                onTodoApp = { navController.navigate("tareas_screen") }
            )
        }

        composable("pomodoro_screen") {
            PomodoroScreen()
        }

        composable("pantalla_saludo/{nombre}") { backStackEntry ->
            val nombre = backStackEntry.arguments?.getString("nombre") ?: "Desconocido"
            PantallaSaludo(nombre = nombre)
        }

        composable("tareas_screen") {
            TareasScreen()
        }
    }
}