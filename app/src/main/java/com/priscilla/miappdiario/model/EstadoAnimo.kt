package com.priscilla.miappdiario.model

data class EstadoAnimo(
    val id: Int,
    val nombre: String
) {
    companion object {
        fun obtenerLista(): List<EstadoAnimo> = listOf(
            EstadoAnimo(1, "Neutral"),
            EstadoAnimo(2, "Contento"),
            EstadoAnimo(3, "Triste"),
            EstadoAnimo(4, "Ansioso"),
            EstadoAnimo(5, "Agradecido"),
            EstadoAnimo(6, "Cansado"),
            EstadoAnimo(7, "Enojado"),
            EstadoAnimo(8, "Tranquilo")
        )
    }


}