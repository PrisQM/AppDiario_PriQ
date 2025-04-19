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

@Composable
fun HistorialScreen() {
    var fechaSeleccionada by remember { mutableStateOf("2025-04-18") }
    var entradaEjemplo by remember { mutableStateOf("Hoy fue un día increíble, aprendí mucho y avancé bastante en mi proyecto.") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
                .heightIn(min = 100.dp)
                .background(Color.White)
                .padding(16.dp)
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
            Button(onClick = { /* Acción para editar */ }) {
                Text("Editar")
            }
            Button(onClick = { /* Acción para eliminar */ }, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEF9A9A))) {
                Text("Eliminar", color = Color.White)
            }
        }
    }
}
