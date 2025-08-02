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
    // 1. Crear el NavController
    // Este controlador vive durante todo el ciclo de vida de la Activity
    val navController = rememberNavController()

    // 2. Crear el NavHost
    // Define el punto de inicio ('startDestination') y las rutas
    NavHost(navController = navController, startDestination = "pantalla_principal") {

        // 3. Definir las rutas (destinations)
        composable("pantalla_principal") {
            PantallaPrincipal(navController = navController)
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