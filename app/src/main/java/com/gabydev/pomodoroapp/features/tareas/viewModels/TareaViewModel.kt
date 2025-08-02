package com.gabydev.pomodoroapp.features.tareas.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.gabydev.pomodoroapp.features.tareas.models.Tarea

class TareasViewModel : ViewModel() {
    // El estado de la lista de tareas.
    // 'by' mutableStateOf permite que Compose observe automáticamente los cambios.
    // 'private set' asegura que solo el ViewModel pueda modificar la lista.
    var listaTareas by mutableStateOf(emptyList<Tarea>())
        private set

    // Un contador para generar IDs únicos para las tareas.
    private var nextId = 0

    // Función para agregar una nueva tarea.
    fun agregarTarea(titulo: String) {
        // Se crea una nueva tarea con un ID único y el título.
        val nuevaTarea = Tarea(id = nextId++, titulo = titulo)
        // Se crea una nueva lista agregando la nueva tarea.
        // Esto mantiene la inmutabilidad.
        listaTareas = listaTareas + nuevaTarea
    }

    // Función para eliminar una tarea.
    fun eliminarTarea(tarea: Tarea) {
        // Se usa filter para crear una nueva lista que excluye la tarea a eliminar.
        listaTareas = listaTareas.filter { it.id != tarea.id } //funciona como un iterable con condicion
        //es como decir, si el elemento actual de la iteracion es diferente del elemento actual, incluyelo
        //en la lista final
    }

    // Función para cambiar el estado 'isCompletada' de una tarea.
    fun alternarEstado(tarea: Tarea) {
        // Se usa map para iterar sobre la lista.
        listaTareas = listaTareas.map {
            // Si el ID coincide, se crea una nueva tarea con el estado invertido.
            if (it.id == tarea.id) {
                it.copy(isCompletada = !it.isCompletada)
            } else {
                // Si no, se mantiene la tarea original sin cambios.
                it
            }
        }
    }
}