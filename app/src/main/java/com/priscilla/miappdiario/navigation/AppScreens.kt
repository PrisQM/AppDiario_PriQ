package com.priscilla.miappdiario.navigation

sealed class AppScreens(val route: String) {
    object Login : AppScreens("login")
    object Entrada : AppScreens("entrada")
    object Signin : AppScreens("signin")
    object Historial : AppScreens("historial")
    object Configuracion : AppScreens("configuracion")
}
