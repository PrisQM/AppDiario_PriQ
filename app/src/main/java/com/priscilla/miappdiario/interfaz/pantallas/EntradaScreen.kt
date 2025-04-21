package com.priscilla.miappdiario.interfaz.pantallas

import android.os.Build
import android.util.Log
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.priscilla.miappdiario.interfaz.componentes.MenuInferior
import com.priscilla.miappdiario.interfaz.tema.ColorBotonPrimario
import com.priscilla.miappdiario.interfaz.tema.ColorTexto
import com.priscilla.miappdiario.model.EntradaDiaria
import com.priscilla.miappdiario.model.EstadoAnimo
import com.priscilla.miappdiario.viewmodel.AuthViewModel
import com.priscilla.miappdiario.viewmodel.EntradaViewModel
import java.time.LocalDate
import java.time.format.TextStyle as JavaTextStyle
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EntradaScreen(
    navController: NavHostController,
    authViewModel: AuthViewModel = viewModel(),
    viewModel: EntradaViewModel = viewModel()
) {
    var entradaTexto by remember { mutableStateOf("") }
    var estadoSeleccionado by remember { mutableStateOf(EstadoAnimo.obtenerLista().first()) }
    var expanded by remember { mutableStateOf(false) }
    var imagenUri by remember { mutableStateOf<Uri?>(null) }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        imagenUri = uri
    }

    val fecha = LocalDate.now()
    val diaSemana = fecha.dayOfWeek.getDisplayName(JavaTextStyle.FULL, Locale("es", "ES"))
    val mes = fecha.month.getDisplayName(JavaTextStyle.FULL, Locale("es", "ES"))
    val fechaFormateada = "${diaSemana.replaceFirstChar { it.uppercase() }}, ${fecha.dayOfMonth} de $mes del ${fecha.year}"

    val snackbarHostState = remember { SnackbarHostState() } // Estado del Snackbar
    val mensaje by viewModel.mensaje.collectAsState()         // Escucha mensaje del ViewModel
    val focusManager = LocalFocusManager.current              // para cerrar teclado
    //val scope = rememberCoroutineScope()

    // Mostrar Snackbar cuando hay mensaje
    LaunchedEffect(mensaje) {
        mensaje?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.limpiarMensaje()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
                .padding(14.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.height(70.dp))

            Text(text = fechaFormateada, style = MaterialTheme.typography.titleLarge, color = ColorTexto)
            Spacer(modifier = Modifier.height(28.dp))

            Text(text = "Escribe sobre tu día...", style = MaterialTheme.typography.bodyLarge, color = ColorTexto)
            Spacer(modifier = Modifier.height(2.dp))

            BasicTextField(
                value = entradaTexto,
                onValueChange = { entradaTexto = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .background(Color.White, shape = MaterialTheme.shapes.medium)
                    .padding(12.dp),
                textStyle = TextStyle(fontSize = 16.sp, color = Color.Black)
            )

            Spacer(modifier = Modifier.height(18.dp))

            Text("¿Cómo te sientes hoy?", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(4.dp))

            Box(modifier = Modifier.fillMaxWidth()) {
                OutlinedButton(onClick = { expanded = true }) {
                    Text(estadoSeleccionado.nombre)
                }

                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    EstadoAnimo.obtenerLista().forEach { estado ->
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

            Spacer(modifier = Modifier.height(20.dp))

            if (imagenUri != null) {
                Button(onClick = { launcher.launch("image/*") }, modifier = Modifier.fillMaxWidth()) {
                    Text("Cambiar Imagen")
                }
                Spacer(modifier = Modifier.height(15.dp))
                Image(
                    painter = rememberAsyncImagePainter(imagenUri),
                    contentDescription = "Foto seleccionada",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                )
            } else {
                Button(onClick = { launcher.launch("image/*") }, modifier = Modifier.fillMaxWidth()) {
                    Text("Seleccionar Imagen")
                }
                Spacer(modifier = Modifier.height(15.dp))
                Text("No has seleccionado ninguna imagen aún")
            }

            Spacer(modifier = Modifier.height(30.dp))

            // Botón de guardar entrada
            Button(
                onClick = {
                    focusManager.clearFocus() // Cierra el teclado
                    //val usuarioEmail = authViewModel.usuario.value?.email ?: "usuario"
                    val entrada = EntradaDiaria(
                        fecha = fecha.toString(),
                        texto = entradaTexto,
                        estadoAnimo = estadoSeleccionado.nombre,
                        imagenUri = imagenUri?.toString()
                    )
                    viewModel.guardarEntrada(entrada)
                    Log.d("GuardarEntrada", "Entrada guardada: $entrada")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = ColorBotonPrimario)
            ) {
                Text("Guardar", color = Color.White)
            }

            Spacer(modifier = Modifier.height(30.dp))

            MenuInferior(navController)
        }
    }
}