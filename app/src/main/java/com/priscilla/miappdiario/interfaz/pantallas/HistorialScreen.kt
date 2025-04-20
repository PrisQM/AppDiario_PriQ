package com.priscilla.miappdiario.interfaz.pantallas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun HistorialScreen(navController: NavHostController) {
    var fechaSeleccionada by remember { mutableStateOf("2025-04-18") }
    var entradaEjemplo by remember { mutableStateOf("Hoy fue un día increíble, aprendí mucho y avancé bastante en mi proyecto.") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = "Historial",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = fechaSeleccionada,
                onValueChange = { fechaSeleccionada = it },
                label = { Text("Seleccionar fecha") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Entrada del día:",
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            ) {
                Text(
                    text = entradaEjemplo,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(12.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = { /* Acción editar */ }) {
                    Text("Editar")
                }
                Button(
                    onClick = { /* Acción eliminar */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEF9A9A))
                ) {
                    Text("Eliminar", color = Color.White)
                }
            }
        }

        // Menú inferior
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(onClick = { navController.navigate("entrada") }) {
                Text("Entrada")
            }
            TextButton(onClick = { navController.navigate("historial") }) {
                Text("Historial")
            }
            TextButton(onClick = { navController.navigate("configuracion") }) {
                Text("Configuración")
            }
        }
    }
}