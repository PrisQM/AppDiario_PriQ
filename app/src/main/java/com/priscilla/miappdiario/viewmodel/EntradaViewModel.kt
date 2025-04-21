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

    // Flujo de estado para almacenar las entradas
    private val _entradas = MutableStateFlow<List<EntradaDiaria>>(emptyList())
    val entradas: StateFlow<List<EntradaDiaria>> get() = _entradas

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

    // Guardar una nueva entrada en Firestore
    fun guardarEntrada(entrada: EntradaDiaria) {
        viewModelScope.launch {
            try {
                repository.guardarEntrada(entrada)
                _mensaje.value = "Entrada guardada exitosamente"
                obtenerEntradas() // Refrescar
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

    // Eliminar una entrada desde Firestore por su ID
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

    // Limpiar el mensaje mostrado
    fun limpiarMensaje() {
        _mensaje.value = null
    }
}