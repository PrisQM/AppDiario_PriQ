package com.priscilla.miappdiario.interfaz.pantallas

import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.foundation.layout.*

import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.priscilla.miappdiario.interfaz.tema.MiAppDiarioTheme

@Composable
fun LoginScreen(navController: NavController) {
    Scaffold { paddingValues ->
        Button(
            onClick = { navController.navigate("entrada") },
            modifier = Modifier.padding(paddingValues)
        ) {
            Text("Entrar al Diario")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    MiAppDiarioTheme {
        LoginScreen(navController = rememberNavController())
    }
}