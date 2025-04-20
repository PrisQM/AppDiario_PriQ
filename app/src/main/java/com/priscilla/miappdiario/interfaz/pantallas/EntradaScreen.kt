package com.priscilla.miappdiario.interfaz.pantallas

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.priscilla.miappdiario.interfaz.tema.ColorBotonPrimario
import com.priscilla.miappdiario.interfaz.tema.ColorTexto
import com.priscilla.miappdiario.model.EstadoAnimo
import java.time.LocalDate
import java.time.format.TextStyle as JavaTextStyle
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EntradaScreen(navController: NavHostController) {
    var entradaTexto by remember { mutableStateOf("") }
    val fecha = LocalDate.now()
    val diaSemana = fecha.dayOfWeek.getDisplayName(JavaTextStyle.FULL, Locale("es", "ES"))
    val mes = fecha.month.getDisplayName(JavaTextStyle.FULL, Locale("es", "ES"))
    val fechaFormateada = "${diaSemana.replaceFirstChar { it.uppercase() }}, ${fecha.dayOfMonth} de ${mes} del ${fecha.year}"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {

        Spacer(modifier = Modifier.height(70.dp))

        //Fecha
        Text(
            text = fechaFormateada,
            style = MaterialTheme.typography.titleLarge,
            color = ColorTexto
        )

        Spacer(modifier = Modifier.height(28.dp))

        Text(
            text = "Escribe sobre tu día...",
            style = MaterialTheme.typography.bodyLarge,
            color = ColorTexto
        )

        Spacer(modifier = Modifier.height(2.dp))

        BasicTextField(
            value = entradaTexto,
            onValueChange = { entradaTexto = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .background(Color.White, shape = MaterialTheme.shapes.medium)
                .padding(12.dp),
            textStyle = TextStyle(fontSize = 16.sp, color = Color.Black)
        )

        Spacer(modifier = Modifier.height(24.dp))

        var estadoSeleccionado by remember { mutableStateOf(EstadoAnimo.obtenerLista().first()) }
        var expanded by remember { mutableStateOf(false) }

        Text("¿Cómo te sientes hoy?", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(4.dp))

        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedButton(onClick = { expanded = true }) {
                Text(estadoSeleccionado.nombre)
            }

            val listaEstados = EstadoAnimo.obtenerLista()

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                listaEstados.forEach { estado ->
                    DropdownMenuItem(
                        text = { Text(estado.nombre) },
                        onClick = {
                            estadoSeleccionado = estado
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = { /* Acción de guardar */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = ColorBotonPrimario)
        ) {
            Text("Guardar", color = Color.White)
        }

        Spacer(modifier = Modifier.height(330.dp))

        // Menú inferior de navegación
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
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