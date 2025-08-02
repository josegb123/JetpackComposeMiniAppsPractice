package com.gabydev.pomodoroapp.features.tareas

import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gabydev.pomodoroapp.features.tareas.models.Tarea
import com.gabydev.pomodoroapp.features.tareas.viewModels.TareasViewModel

@Composable
fun TareasScreen(viewModel: TareasViewModel = viewModel()) {
    // Estado local para el TextField
    var nuevaTarea by remember { mutableStateOf("") }
    // El estado de la lista de tareas del ViewModel
    val listaTareas = viewModel.listaTareas

    TareasContent(
        listaTareas = listaTareas,
        nuevaTarea = nuevaTarea,
        onNuevaTareaChange = { nuevaTarea = it },
        onAgregarTarea = {
            if (nuevaTarea.isNotBlank()) {
                viewModel.agregarTarea(nuevaTarea)
                nuevaTarea = ""
            }
        },
        onAlternarEstado = viewModel::alternarEstado, // Referencia a la función
        onEliminarTarea = viewModel::eliminarTarea,
        onAlternarExpansion = viewModel::alternarExpansion
    )
}

@Composable
fun TareasContent(
    listaTareas: List<Tarea>,
    nuevaTarea: String,
    onNuevaTareaChange: (String) -> Unit,
    onAgregarTarea: () -> Unit,
    onAlternarEstado: (Tarea) -> Unit,
    onEliminarTarea: (Tarea) -> Unit,
    onAlternarExpansion: (Tarea) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.padding(32.dp))

        Text(
            text = "Todo App",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.padding(32.dp))

        OutlinedTextField(
            value = nuevaTarea,
            onValueChange = onNuevaTareaChange,
            placeholder = { Text("Nueva tarea...") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        Button(
            onClick = onAgregarTarea,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Agregar Tarea")
        }

        Spacer(Modifier.height(16.dp))

        LazyColumn {
            items(listaTareas) { tarea ->
                TareaItem(
                    tarea = tarea,
                    onToggle = { onAlternarEstado(tarea) },
                    onDelete = { onEliminarTarea(tarea) },
                    onExpansion = { onAlternarExpansion(tarea) }
                )
            }
        }
    }
}


@Composable
fun TareaItem(
    tarea: Tarea,
    onToggle: () -> Unit,
    onDelete: () -> Unit,
    onExpansion: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = tarea.isCompletada,
            onCheckedChange = { onToggle() }
        )

        Text(
            maxLines = if (tarea.isExpandida) Int.MAX_VALUE else 1,
            text = tarea.titulo,
            modifier = Modifier
                .weight(1f)
                .clickable {
                    onExpansion()
                },
            textDecoration = if (tarea.isCompletada) TextDecoration.LineThrough else null
        )

        IconButton(onClick = onDelete) {
            Icon(Icons.Default.Delete, contentDescription = "Eliminar")
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun TareasContentPreview() {
    val dummyTareas = listOf(
        Tarea(1, "Comprar leche", isCompletada = true, isExpandida = true),
        Tarea(2, "Aprender Compose", isExpandida = false),
        Tarea(3, "Hacer ejercicio", isExpandida = false)
    )

    TareasContent(
        listaTareas = dummyTareas,
        nuevaTarea = "Tarea de prueba",
        onNuevaTareaChange = {},
        onAgregarTarea = {},
        onAlternarEstado = {},
        onEliminarTarea = {},
        onAlternarExpansion = {}
    )
}