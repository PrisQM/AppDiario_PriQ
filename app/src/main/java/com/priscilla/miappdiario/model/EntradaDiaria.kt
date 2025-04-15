package com.priscilla.miappdiario.model

data class EntradaDiaria(
    val id: String = "",
    val fecha: String = "",
    val pensamiento: String = "",
    val urlFoto: String = "",
    val estadoAnimo: EstadoAnimo = EstadoAnimo()
)