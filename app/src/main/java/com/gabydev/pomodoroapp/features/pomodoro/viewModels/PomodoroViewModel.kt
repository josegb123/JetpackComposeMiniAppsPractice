package com.gabydev.pomodoroapp.features.pomodoro.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gabydev.pomodoroapp.features.pomodoro.models.TemporizadorEstado
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PomodoroViewModel : ViewModel() {

    /**
     * Estado interno y mutable del temporizador.
     *
     * Este [MutableStateFlow] contiene el estado actual del temporizador, representado por
     * un objeto [TemporizadorEstado]. Se inicializa con un tiempo restante de 1,500,000 milisegundos (25 minutos).
     *
     * Al ser un `MutableStateFlow`, permite que el estado del temporizador sea modificado internamente
     * por la clase que lo contiene (por ejemplo, al iniciar, pausar o reiniciar el temporizador).
     *
     * Las actualizaciones a este `StateFlow` notificarán a cualquier observador (por ejemplo, a través de
     * `estadoTemporizador.collect { ... }`) sobre los cambios en el estado del temporizador.
     *
     * Es privado (`private val`) para asegurar que la modificación del estado solo pueda ocurrir
     * dentro de la clase que lo define, promoviendo una mejor encapsulación.
     * La exposición pública del estado se suele hacer a través de una propiedad `StateFlow` inmutable.
     *
     * @see TemporizadorEstado
     * @see StateFlow
     * @see MutableStateFlow
     */// (Necesitas importar kotlinx.coroutines.flow.StateFlow)
    private val _estadoTemporizador =
        MutableStateFlow(TemporizadorEstado(tiempoRestante = 1500000L))


    /**
     * Flujo de estado público que expone el estado actual del temporizador.
     *
     * Este `StateFlow` emite instancias de `TemporizadorEstado`, que representan
     * el estado actual del temporizador (por ejemplo, pausado, corriendo, finalizado) y
     * el tiempo restante.
     *
     * Los Composables pueden observar este flujo para reaccionar a los cambios en el
     * estado del temporizador y actualizar la interfaz de usuario en consecuencia.
     *
     * Se deriva del `_estadoTemporizador` (MutableStateFlow privado) utilizando `asStateFlow()`,
     * lo que lo hace de solo lectura para los consumidores externos.
     */// El Composable observará este estado
    val estadoTemporizador: StateFlow<TemporizadorEstado> = _estadoTemporizador.asStateFlow()

    /**
     * Representa el trabajo (job) de la corrutina en segundo plano para el temporizador.
     *
     * Este trabajo se utiliza para gestionar el ciclo de vida de la tarea en segundo plano del temporizador.
     * Se puede iniciar, cancelar y verificar su estado (por ejemplo, activo, completado).
     *
     * - Es anulable (`Job?`) porque el temporizador podría no estar en ejecución,
     *   en cuyo caso `jobTemporizador` será `null`.
     * - Cuando se inicia el temporizador, se asigna una nueva instancia de `Job` a esta propiedad.
     * - Cuando el temporizador se detiene o se cancela, este trabajo debe cancelarse
     *   y establecerse de nuevo en `null` para liberar recursos.
     */
    private var jobTemporizador: Job? = null

    /**
     * Inicia el temporizador.
     *
     * Si no hay un trabajo de temporizador activo, crea uno nuevo.
     * El trabajo decrementará el `_estadoTemporizador.value.tiempoRestante` en 1000 milisegundos (1 segundo)
     * cada segundo hasta que `tiempoRestante` sea 0.
     *
     * Durante la cuenta regresiva, `_estadoTemporizador.value.estaCorriendo` se establece en `true`.
     * Cuando el temporizador finaliza (tiempoRestante llega a 0), `_estadoTemporizador.value.estaCorriendo`
     * se establece en `false`.
     *
     * Utiliza `viewModelScope.launch` para ejecutar la cuenta regresiva en una corrutina,
     * permitiendo que la UI permanezca responsiva.
     */
    fun iniciarTemporizador() {
        if (jobTemporizador == null || jobTemporizador?.isActive == false) {
            jobTemporizador = viewModelScope.launch {
                // (Necesitas importar kotlinx.coroutines.delay)
                while (_estadoTemporizador.value.tiempoRestante > 0) {
                    delay(1000L) // Espera 1 segundo
                    _estadoTemporizador.value = _estadoTemporizador.value.copy(
                        tiempoRestante = _estadoTemporizador.value.tiempoRestante - 1000L,
                        estaCorriendo = true
                    )
                }
                _estadoTemporizador.value = _estadoTemporizador.value.copy(estaCorriendo = false)
            }
        }
    }

    fun reiniciarTemporizador() {
        jobTemporizador?.cancel() // Cancela la corrutina si está corriendo
        _estadoTemporizador.value =
            TemporizadorEstado(tiempoRestante = 1500000L, estaCorriendo = false)
    }

    fun pararTemporizador() {
        if (jobTemporizador?.isActive == true) {
            jobTemporizador?.cancel()
            _estadoTemporizador.value = _estadoTemporizador.value.copy(estaCorriendo = false)
        } else {
            _estadoTemporizador.value = _estadoTemporizador.value.copy(estaCorriendo = true)
            iniciarTemporizador()
        }

    }
}