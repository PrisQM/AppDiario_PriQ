package com.priscilla.miappdiario.interfaz.pantallas

import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.foundation.layout.*

@Composable
fun EntradaScreen(navController: NavController) {
    Scaffold { paddingValues ->
        Text(
            text = "Bienvenida a tu entrada del día",
            modifier = Modifier.padding(paddingValues)
        )
    }
}