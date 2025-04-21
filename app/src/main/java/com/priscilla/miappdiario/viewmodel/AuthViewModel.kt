package com.priscilla.miappdiario.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.priscilla.miappdiario.data.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val repository: AuthRepository = AuthRepository()
) : ViewModel() {

    // Estado que guarda el usuario actual (nulo si no hay sesión iniciada)
    private val _usuario = MutableStateFlow<FirebaseUser?>(null)
    val usuario: StateFlow<FirebaseUser?> get() = _usuario

    // Estado para manejar mensajes de error (por ejemplo, credenciales inválidas)
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error

    // Lógica para iniciar sesión con correo y contraseña
    fun login(email: String, password: String) {
        viewModelScope.launch {
            val resultado = repository.login(email, password)
            resultado
                .onSuccess { user ->
                    _usuario.value = user // Usuario autenticado
                    _error.value = null   // Limpia errores anteriores
                }
                .onFailure { e ->
                    _error.value = e.message // Muestra mensaje de error
                }
        }
    }

    // Lógica para registrar nuevo usuario
    fun register(email: String, password: String) {
        viewModelScope.launch {
            val resultado = repository.register(email, password)
            resultado
                .onSuccess { user ->
                    _usuario.value = user
                    _error.value = null
                }
                .onFailure { e ->
                    _error.value = e.message
                }
        }
    }

    // Lógica para cerrar sesión
    fun logout() {
        repository.logout()
        _usuario.value = null
    }
}