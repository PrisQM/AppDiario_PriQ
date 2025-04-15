package com.priscilla.miappdiario.navigation

sealed class AppScreens(val route: String) {
    object Login : AppScreens("login")
    object Registro : AppScreens("registro")
    object Entrada : AppScreens("entrada")
    object Historial : AppScreens("historial")
    object Configuracion : AppScreens("configuracion")
}
