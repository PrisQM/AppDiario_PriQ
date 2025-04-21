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
import com.priscilla.miappdiario.interfaz.componentes.MenuInferior

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts

import coil.compose.rememberAsyncImagePainter
import androidx.compose.foundation.Image

import androidx.lifecycle.viewmodel.compose.viewModel
import com.priscilla.miappdiario.viewmodel.EntradaViewModel
import com.priscilla.miappdiario.model.EntradaDiaria

import android.util.Log

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EntradaScreen(navController: NavHostController) {
    var entradaTexto by remember { mutableStateOf("") }
    val fecha = LocalDate.now()
    val diaSemana = fecha.dayOfWeek.getDisplayName(JavaTextStyle.FULL, Locale("es", "ES"))
    val mes = fecha.month.getDisplayName(JavaTextStyle.FULL, Locale("es", "ES"))
    val fechaFormateada = "${diaSemana.replaceFirstChar { it.uppercase() }}, ${fecha.dayOfMonth} de ${mes} del ${fecha.year}"

    var imagenUri by remember { mutableStateOf<Uri?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imagenUri = uri
    }

    val viewModel: EntradaViewModel = viewModel()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(14.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Spacer(modifier = Modifier.height(70.dp))

        //Label de Fecha
        Text(
            text = fechaFormateada,
            style = MaterialTheme.typography.titleLarge,
            color = ColorTexto
        )

        Spacer(modifier = Modifier.height(28.dp))

        //Label de entrada
        Text(
            text = "Escribe sobre tu día...",
            style = MaterialTheme.typography.bodyLarge,
            color = ColorTexto
        )

        Spacer(modifier = Modifier.height(2.dp))

        //Espacio para la entrada
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

        var estadoSeleccionado by remember { mutableStateOf(EstadoAnimo.obtenerLista().first()) }
        var expanded by remember { mutableStateOf(false) }

        //Label de estado de ánimo
        Text("¿Cómo te sientes hoy?", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(4.dp))

        //Dropdown de estado de ánimo
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

        Spacer(modifier = Modifier.height(20.dp))

        //Imagen seleccionada
        if (imagenUri != null) {

            //Botón de seleccionar imagen
            Button(
                onClick = { launcher.launch("image/*") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
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
            //Botón de seleccionar imagen
            Button(
                onClick = { launcher.launch("image/*") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text("Seleccionar Imagen")
            }

            Spacer(modifier = Modifier.height(15.dp))

            Text("No has seleccionado ninguna imagen aún")
        }

        Spacer(modifier = Modifier.height(30.dp))

        //Botón de guardar
        Button(
            onClick = { /* Acción de guardar */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = ColorBotonPrimario)
        ) {
            Text("Guardar", color = Color.White)
        }

        Spacer(modifier = Modifier.height(30.dp))

        //menú inferior
        MenuInferior(navController)

    }
}