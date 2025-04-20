package com.priscilla.miappdiario.interfaz.componentes

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.priscilla.miappdiario.navigation.AppScreens

@Composable
fun MenuInferior(navController: NavHostController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextButton(onClick = { navController.navigate(AppScreens.Entrada.route) }) {
            Text("Entrada")
        }
        TextButton(onClick = { navController.navigate(AppScreens.Historial.route) }) {
            Text("Historial")
        }
        TextButton(onClick = { navController.navigate(AppScreens.Configuracion.route) }) {
            Text("Configuración")
        }
    }
}