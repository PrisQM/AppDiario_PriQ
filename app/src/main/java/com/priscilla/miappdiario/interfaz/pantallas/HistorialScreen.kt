package com.priscilla.miappdiario.interfaz.pantallas

import android.app.DatePickerDialog
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import com.priscilla.miappdiario.model.EntradaDiaria
import com.priscilla.miappdiario.model.EstadoAnimo
import com.priscilla.miappdiario.viewmodel.EntradaViewModel
import kotlinx.coroutines.launch
import java.util.*

@Composable
fun HistorialScreen(navController: NavHostController, viewModel: EntradaViewModel = viewModel()) {
    val context = LocalContext.current
    val calendario = Calendar.getInstance()

    LaunchedEffect(Unit) {
        viewModel.obtenerEntradas()
    }

    var fechaSeleccionada by remember {
        mutableStateOf(
            "${calendario.get(Calendar.YEAR)}-${(calendario.get(Calendar.MONTH) + 1).toString().padStart(2, '0')}-${calendario.get(Calendar.DAY_OF_MONTH).toString().padStart(2, '0')}"
        )
    }

    var mostrarDialogoEliminar by remember { mutableStateOf(false) }
    var mostrarDialogoEditar by remember { mutableStateOf(false) }
    var modoEdicion by remember { mutableStateOf(false) }

    // Estado de edición
    var textoEditado by remember { mutableStateOf("") }
    var estadoEditado by remember { mutableStateOf(EstadoAnimo.obtenerLista().first()) }
    var imagenEditada by remember { mutableStateOf<Uri?>(null) }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        imagenEditada = uri
    }

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val entradas by viewModel.entradas.collectAsState()
    val entradaFiltrada = entradas.find { it.fecha == fechaSeleccionada }

    Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }) { padding ->
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
                                    val mes = (month + 1).toString().padStart(2, '0')
                                    val dia = day.toString().padStart(2, '0')
                                    fechaSeleccionada = "$year-$mes-$dia"
                                    modoEdicion = false
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
                    if (modoEdicion) {
                        // Campo de texto editable
                        OutlinedTextField(
                            value = textoEditado,
                            onValueChange = { textoEditado = it },
                            label = { Text("Resumen del día") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text("Estado de ánimo:")
                        var expanded by remember { mutableStateOf(false) }
                        Box {
                            OutlinedButton(onClick = { expanded = true }) {
                                Text(estadoEditado.nombre)
                            }
                            DropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false }
                            ) {
                                EstadoAnimo.obtenerLista().forEach {
                                    DropdownMenuItem(
                                        text = { Text(it.nombre) },
                                        onClick = {
                                            estadoEditado = it
                                            expanded = false
                                        }
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Imagen editable
                        Button(onClick = { launcher.launch("image/*") }, modifier = Modifier.fillMaxWidth()) {
                            Text("Seleccionar imagen")
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        imagenEditada?.let {
                            Image(
                                painter = rememberAsyncImagePainter(it),
                                contentDescription = "Imagen seleccionada",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(180.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        Button(
                            onClick = {
                                val entradaActualizada = entradaFiltrada.copy(
                                    texto = textoEditado,
                                    estadoAnimo = estadoEditado.nombre,
                                    imagenUri = imagenEditada?.toString()
                                )
                                viewModel.editarEntrada(entradaActualizada)
                                modoEdicion = false
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar("Entrada actualizada correctamente")
                                }
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Actualizar")
                        }
                    } else {
                        // Vista normal
                        Card(modifier = Modifier.fillMaxWidth().padding(4.dp)) {
                            Text(
                                text = entradaFiltrada.texto,
                                fontSize = 14.sp,
                                modifier = Modifier.padding(12.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text("Estado de ánimo: ${entradaFiltrada.estadoAnimo}", fontSize = 14.sp, fontWeight = FontWeight.Medium)

                        Spacer(modifier = Modifier.height(12.dp))

                        entradaFiltrada.imagenUri?.let {
                            Image(
                                painter = rememberAsyncImagePainter(it),
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
                            Button(onClick = { mostrarDialogoEditar = true }) {
                                Text("Editar")
                            }
                            Button(
                                onClick = { mostrarDialogoEliminar = true },
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                            ) {
                                Text("Eliminar", color = MaterialTheme.colorScheme.onError)
                            }
                        }
                    }

                    // Confirmación de eliminar
                    if (mostrarDialogoEliminar) {
                        AlertDialog(
                            onDismissRequest = { mostrarDialogoEliminar = false },
                            title = { Text("¿Eliminar entrada?") },
                            text = { Text("¿Estás segura de que querés eliminar esta entrada? Esta acción no se puede deshacer.") },
                            confirmButton = {
                                TextButton(onClick = {
                                    viewModel.eliminarEntrada(entradaFiltrada.fecha)
                                    mostrarDialogoEliminar = false
                                    coroutineScope.launch {
                                        snackbarHostState.showSnackbar("Entrada eliminada correctamente")
                                    }
                                }) {
                                    Text("Eliminar", color = MaterialTheme.colorScheme.error)
                                }
                            },
                            dismissButton = {
                                TextButton(onClick = { mostrarDialogoEliminar = false }) {
                                    Text("Cancelar")
                                }
                            }
                        )
                    }

                    // Confirmación de editar
                    if (mostrarDialogoEditar) {
                        AlertDialog(
                            onDismissRequest = { mostrarDialogoEditar = false },
                            title = { Text("¿Editar entrada?") },
                            text = { Text("¿Querés editar esta entrada para agregar o modificar información?") },
                            confirmButton = {
                                TextButton(onClick = {
                                    mostrarDialogoEditar = false
                                    modoEdicion = true
                                    textoEditado = entradaFiltrada.texto
                                    estadoEditado = EstadoAnimo.obtenerLista().find { it.nombre == entradaFiltrada.estadoAnimo } ?: EstadoAnimo.obtenerLista().first()
                                    imagenEditada = entradaFiltrada.imagenUri?.let { Uri.parse(it) }
                                }) {
                                    Text("Editar")
                                }
                            },
                            dismissButton = {
                                TextButton(onClick = { mostrarDialogoEditar = false }) {
                                    Text("Cancelar")
                                }
                            }
                        )
                    }
                } else {
                    Text("No hay ninguna entrada para esta fecha.")
                }
            }

            MenuInferior(navController)
        }
    }
}