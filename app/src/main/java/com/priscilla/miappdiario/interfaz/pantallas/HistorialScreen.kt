package com.priscilla.miappdiario.interfaz.pantallas

import android.app.DatePickerDialog
import androidx.compose.foundation.Image
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
import coil.compose.rememberAsyncImagePainter
import com.priscilla.miappdiario.interfaz.componentes.MenuInferior
import com.priscilla.miappdiario.viewmodel.EntradaViewModel
import kotlinx.coroutines.launch
import java.util.*

@Composable
fun HistorialScreen(navController: NavHostController, viewModel: EntradaViewModel = viewModel()) {
    val context = LocalContext.current
    val calendario = Calendar.getInstance()

    // Se carga una sola vez al iniciar la pantalla
    LaunchedEffect(Unit) {
        viewModel.obtenerEntradas()
    }

    // Fecha seleccionada por el usuario
    var fechaSeleccionada by remember {
        mutableStateOf(
            "${calendario.get(Calendar.YEAR)}-${(calendario.get(Calendar.MONTH) + 1).toString().padStart(2, '0')}-${calendario.get(Calendar.DAY_OF_MONTH).toString().padStart(2, '0')}"
        )
    }

    // Control del diálogo de confirmación
    var mostrarDialogoConfirmacion by remember { mutableStateOf(false) }

    // Snackbar y su alcance de corrutina
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    // Entradas desde ViewModel y filtro por fecha
    val entradas by viewModel.entradas.collectAsState()
    val entradaFiltrada = entradas.find { it.fecha == fechaSeleccionada }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text("Historial", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(24.dp))

                // Selector de fecha con DatePickerDialog
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

                if (entradaFiltrada != null) {
                    // Muestra la entrada de texto
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

                    // Estado de ánimo
                    Text(
                        text = "Estado de ánimo: ${entradaFiltrada.estadoAnimo}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Imagen del día (si existe)
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

                    // Botones de acción
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(onClick = { /* Acción de edición futura */ }) {
                            Text("Editar")
                        }
                        Button(
                            onClick = {
                                mostrarDialogoConfirmacion = true
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                        ) {
                            Text("Eliminar", color = MaterialTheme.colorScheme.onError)
                        }
                    }

                    // Diálogo de confirmación para eliminar entrada
                    if (mostrarDialogoConfirmacion) {
                        AlertDialog(
                            onDismissRequest = { mostrarDialogoConfirmacion = false },
                            title = { Text("¿Eliminar entrada?") },
                            text = { Text("¿Estás segura de que querés eliminar esta entrada? Esta acción no se puede deshacer.") },
                            confirmButton = {
                                TextButton(onClick = {
                                    viewModel.eliminarEntrada(entradaFiltrada.fecha)
                                    mostrarDialogoConfirmacion = false
                                    coroutineScope.launch {
                                        snackbarHostState.showSnackbar("Entrada eliminada correctamente")
                                    }
                                }) {
                                    Text("Eliminar", color = MaterialTheme.colorScheme.error)
                                }
                            },
                            dismissButton = {
                                TextButton(onClick = { mostrarDialogoConfirmacion = false }) {
                                    Text("Cancelar")
                                }
                            }
                        )
                    }

                } else {
                    Text("No hay ninguna entrada para esta fecha.")
                }
            }

            // Menú inferior reutilizable
            MenuInferior(navController)
        }
    }
}