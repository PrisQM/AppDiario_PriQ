package com.priscilla.miappdiario.interfaz.pantallas

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.priscilla.miappdiario.interfaz.componentes.MenuInferior
import com.priscilla.miappdiario.navigation.AppScreens
import com.priscilla.miappdiario.viewmodel.AuthViewModel

@Composable
fun ConfiguracionScreen(
    navController: NavHostController,
    viewModel: AuthViewModel = viewModel() // ViewModel para manejar sesión
) {
    var hora by remember { mutableStateOf(20) }
    var minutos by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Configuración",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(text = "Notificación diaria:", fontSize = 16.sp)

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                OutlinedTextField(
                    value = hora.toString().padStart(2, '0'),
                    onValueChange = { value -> hora = value.toIntOrNull() ?: hora },
                    label = { Text("Hora") },
                    modifier = Modifier.width(100.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                OutlinedTextField(
                    value = minutos.toString().padStart(2, '0'),
                    onValueChange = { value -> minutos = value.toIntOrNull() ?: minutos },
                    label = { Text("Minutos") },
                    modifier = Modifier.width(100.dp)
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Botón para cerrar sesión y navegar al login limpiando el historial
            Button(
                onClick = {
                    viewModel.logout() // Llama a signOut de Firebase
                    navController.navigate(AppScreens.Login.route) {
                        popUpTo(AppScreens.Entrada.route) { inclusive = true } // Limpia el backstack
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFADCBE3)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Cerrar sesión")
            }
        }

        // Menú inferior común
        MenuInferior(navController)
    }
}
