package com.priscilla.miappdiario.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavController
import com.priscilla.miappdiario.interfaz.pantallas.*


@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = AppScreens.Login.route
    ) {
        composable(route = AppScreens.Login.route) {
            LoginScreen(navController)
        }
        composable(route = AppScreens.Entrada.route) {
            EntradaScreen(navController)
        }
        composable(route = AppScreens.Signin.route) {
            SigninScreen(navController)
        }
        composable(route = AppScreens.Historial.route) {
            HistorialScreen()
        }
        composable(route = AppScreens.Configuracion.route) {
            ConfiguracionScreen()
        }
    }
}