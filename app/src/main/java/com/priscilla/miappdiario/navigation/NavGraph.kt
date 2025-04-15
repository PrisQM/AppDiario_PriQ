package com.priscilla.miappdiario.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavController
import com.priscilla.miappdiario.interfaz.pantallas.LoginScreen
import com.priscilla.miappdiario.interfaz.pantallas.EntradaScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable



@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(navController)
        }
        composable("entrada") {
            EntradaScreen(navController)
        }
    }
}