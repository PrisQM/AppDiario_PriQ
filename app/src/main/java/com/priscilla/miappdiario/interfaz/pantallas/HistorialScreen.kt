package com.priscilla.miappdiario.interfaz.pantallas

import android.app.DatePickerDialog
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.priscilla.miappdiario.interfaz.componentes.MenuInferior
import com.priscilla.miappdiario.viewmodel.EntradaViewModel
import java.util.*
import coil.compose.rememberAsyncImagePainter
import androidx.compose.foundation.Image

@Composable
fun HistorialScreen(navController: NavHostController, viewModel: EntradaViewModel = viewModel()) {
    val context = LocalContext.current
    val calendario = Calendar.getInstance()

    LaunchedEffect(Unit) {
        viewModel.obtenerEntradas()
    }

    // Fecha seleccionada (por defecto hoy)
    var fechaSeleccionada by remember {
        mutableStateOf(
            "${calendario.get(Calendar.YEAR)}-${(calendario.get(Calendar.MONTH) + 1).toString().padStart(2, '0')}-${calendario.get(Calendar.DAY_OF_MONTH).toString().padStart(2, '0')}"
        )
    }

    // Se obtiene la lista de entradas desde el ViewModel
    val entradas by viewModel.entradas.collectAsState()

    // Filtra la entrada según la fecha seleccionada
    val entradaFiltrada = entradas.find { it.fecha == fechaSeleccionada }

    // UI principal
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text("Historial", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(24.dp))

            // Campo de fecha y botón para abrir el DatePicker
            OutlinedTextField(
                value = fechaSeleccionada,
                onValueChange = {},
                label = { Text("Seleccionar fecha") },
                modifier = Modifier.fillMaxWidth(),
                readOnly = true,
                trailingIcon = {
                    TextButton(onClick = {
                        DatePickerDialog(
                            context,
                            { _, year, month, day ->
                                val mesFormateado = (month + 1).toString().padStart(2, '0')
                                val diaFormateado = day.toString().padStart(2, '0')
                                fechaSeleccionada = "$year-$mesFormateado-$diaFormateado"
                            },
                            calendario.get(Calendar.YEAR),
                            calendario.get(Calendar.MONTH),
                            calendario.get(Calendar.DAY_OF_MONTH)
                        ).show()
                    }) {
                        Text("Elegir")
                    }
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text("Entrada del día:", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(8.dp))

            // Muestra la entrada si existe
            if (entradaFiltrada != null) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                ) {
                    Text(
                        text = entradaFiltrada.texto,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(12.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Mostrar el estado de ánimo
                Text(
                    text = "Estado de ánimo: ${entradaFiltrada.estadoAnimo}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Mostrar la imagen si existe
                entradaFiltrada.imagenUri?.let { uri ->
                    Image(
                        painter = rememberAsyncImagePainter(uri),
                        contentDescription = "Imagen del día",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
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
                        onClick = {
                            viewModel.eliminarEntrada(entradaFiltrada.id)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                    ) {
                        Text("Eliminar", color = MaterialTheme.colorScheme.onError)
                    }
                }
            } else {
                Text("No hay ninguna entrada para esta fecha.")
            }
        }

        MenuInferior(navController)
    }
}