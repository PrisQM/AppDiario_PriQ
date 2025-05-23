package com.priscilla.miappdiario.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.priscilla.miappdiario.model.EntradaDiaria
import com.priscilla.miappdiario.data.EntradaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EntradaViewModel(
    private val repository: EntradaRepository = EntradaRepository()
) : ViewModel() {

    // Flujo de estado para almacenar todas las entradas
    private val _entradas = MutableStateFlow<List<EntradaDiaria>>(emptyList())
    val entradas: StateFlow<List<EntradaDiaria>> get() = _entradas

    // Flujo de estado para almacenar una entrada específica por fecha
    private val _entradaPorFecha = MutableStateFlow<EntradaDiaria?>(null)
    val entradaPorFecha: StateFlow<EntradaDiaria?> get() = _entradaPorFecha

    // Flujo de estado para mostrar errores o mensajes
    private val _mensaje = MutableStateFlow<String?>(null)
    val mensaje: StateFlow<String?> get() = _mensaje

    // Obtener todas las entradas desde Firestore
    fun obtenerEntradas() {
        viewModelScope.launch {
            try {
                _entradas.value = repository.obtenerEntradas()
            } catch (e: Exception) {
                _mensaje.value = "Error al obtener entradas: ${e.message}"
            }
        }
    }

    // Obtener una entrada específica por su fecha (formato yyyy-MM-dd)
    fun cargarEntradaPorFecha(fecha: String) {
        viewModelScope.launch {
            try {
                val entrada = repository.obtenerEntradaPorFecha(fecha)
                _entradaPorFecha.value = entrada
            } catch (e: Exception) {
                _mensaje.value = "Error al buscar entrada: ${e.message}"
            }
        }
    }

    // Guardar una nueva entrada en Firestore
    fun guardarEntrada(entrada: EntradaDiaria) {
        viewModelScope.launch {
            try {
                repository.guardarEntrada(entrada)
                _mensaje.value = "Entrada guardada exitosamente"
                obtenerEntradas() // Refrescar lista
            } catch (e: Exception) {
                _mensaje.value = "Error al guardar entrada: ${e.message}"
            }
        }
    }

    // Editar una entrada existente en Firestore
    fun editarEntrada(entrada: EntradaDiaria) {
        viewModelScope.launch {
            try {
                repository.editarEntrada(entrada)
                _mensaje.value = "Entrada editada exitosamente"
                obtenerEntradas()
            } catch (e: Exception) {
                _mensaje.value = "Error al editar entrada: ${e.message}"
            }
        }
    }

    // Eliminar una entrada desde Firestore por su ID (fecha)
    fun eliminarEntrada(id: String) {
        viewModelScope.launch {
            try {
                repository.eliminarEntrada(id)
                _mensaje.value = "Entrada eliminada exitosamente"
                obtenerEntradas()
            } catch (e: Exception) {
                _mensaje.value = "Error al eliminar entrada: ${e.message}"
            }
        }
    }

    // Limpiar el mensaje mostrado en pantalla
    fun limpiarMensaje() {
        _mensaje.value = null
    }
}