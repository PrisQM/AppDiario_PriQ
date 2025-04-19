package com.priscilla.miappdiario.model

data class EntradaDiaria(
    val id: String = "",               // Puedes usar un UUID luego
    val nombre: String = "",           // Nombre del usuario o título
    val texto: String = "",            // Resumen del día
    val fecha: String = "",            // Fecha de la entrada
    val estadoAnimo: String = "",      // Estado de ánimo (Feliz, Triste, etc.)
    val imagenUri: String? = null      // Imagen opcional puede ser null

)

//CAMBIAR ESTA OPCIÓN PARA QUE NO SE PUEDA HACER LA ENTRADA SIN VARIOS DE ESTOS DATOS
//Me parecería sabio que solo se pueda poner una entrada, que el id sea la fecha.

//✅ Este diseño incluye valores por defecto para que no dé error si olvidás
// pasarlos en algún momento (por ejemplo, en pruebas o previsualizaciones).