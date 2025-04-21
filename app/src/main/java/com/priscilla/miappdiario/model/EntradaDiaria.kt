package com.priscilla.miappdiario.model

data class EntradaDiaria(
    val fecha: String = "",            // La fecha ahora es el ID único
    val texto: String = "",            // Resumen del día
    val estadoAnimo: String = "",      // Estado de ánimo
    val imagenUri: String? = null      // Imagen opcional
)
