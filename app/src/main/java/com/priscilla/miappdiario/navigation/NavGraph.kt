package com.priscilla.miappdiario.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavController
import com.priscilla.miappdiario.interfaz.pantallas.LoginScreen
import com.priscilla.miappdiario.interfaz.pantallas.EntradaScreen


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
    }
}