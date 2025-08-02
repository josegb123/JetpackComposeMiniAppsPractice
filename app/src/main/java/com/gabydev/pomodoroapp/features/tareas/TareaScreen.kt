package com.gabydev.pomodoroapp.features.tareas

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gabydev.pomodoroapp.features.tareas.models.Tarea
import com.gabydev.pomodoroapp.features.tareas.viewModels.TareasViewModel

// La pantalla principal para la aplicación de tareas.
@Composable
fun TareasScreen(viewModel: TareasViewModel = viewModel()) {
    // Estado local para el texto del TextField.
    var nuevaTarea by remember { mutableStateOf("") }
    // Se observa la lista de tareas del ViewModel.
    val listaTareas = viewModel.listaTareas

    Column(
        modifier = Modifier
            .fillMaxWidth() // Ocupa todo el ancho disponible
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Campo de texto para que el usuario escriba la nueva tarea.
        OutlinedTextField(
            value = nuevaTarea,
            onValueChange = { nuevaTarea = it }, // Actualiza el estado local
            placeholder = { Text("Nueva tarea...") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        // Botón para agregar una tarea.
        Button(
            onClick = {
                // Si el texto no está vacío, se agrega la tarea al ViewModel.
                if (nuevaTarea.isNotBlank()) {
                    viewModel.agregarTarea(nuevaTarea)
                    nuevaTarea = "" // Se limpia el campo de texto
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Agregar Tarea")
        }

        Spacer(Modifier.height(16.dp))

        // LazyColumn es un Composable eficiente para listas largas.
        LazyColumn {
            // Itera sobre la lista de tareas del ViewModel.
            items(listaTareas) { tarea ->
                // Muestra cada tarea en un Composable 'TareaItem'.
                TareaItem(
                    tarea = tarea,
                    onToggle = { viewModel.alternarEstado(tarea) }, // Llama a la función del ViewModel
                    onDelete = { viewModel.eliminarTarea(tarea) } // Llama a la función del ViewModel
                )
            }
        }
    }
}

// Un Composable para un solo ítem de la lista.
@Composable
fun TareaItem(tarea: Tarea, onToggle: () -> Unit, onDelete: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Checkbox para marcar la tarea como completada.
        Checkbox(
            checked = tarea.isCompletada,
            onCheckedChange = { onToggle() } // El callback notifica a la función onToggle
        )
        // El texto de la tarea.
        Text(
            text = tarea.titulo,
            modifier = Modifier.weight(1f), // Ocupa todo el espacio restante
            // Si la tarea está completada, se le añade un tachado.
            textDecoration = if (tarea.isCompletada) TextDecoration.LineThrough else null
        )
        // Botón para eliminar la tarea.
        IconButton(onClick = onDelete) {
            Icon(Icons.Default.Delete, contentDescription = "Eliminar")
        }
    }
}