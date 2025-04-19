package com.priscilla.miappdiario.model

data class EstadoAnimo(
    val id: Int,
    val nombre: String
) {
    companion object {
        fun obtenerLista(): List<EstadoAnimo> = listOf(
            EstadoAnimo(1, "Feliz"),
            EstadoAnimo(2, "Triste"),
            EstadoAnimo(3, "Neutral"),
            EstadoAnimo(4, "Ansioso"),
            EstadoAnimo(5, "Agradecida"),
            EstadoAnimo(6, "Cansado"),
            EstadoAnimo(7, "Enojado"),
            EstadoAnimo(8, "Tranquilo")
        )
    }
}