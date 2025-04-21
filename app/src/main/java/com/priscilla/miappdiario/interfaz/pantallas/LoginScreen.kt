package com.priscilla.miappdiario.interfaz.pantallas

import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.priscilla.miappdiario.navigation.AppScreens
import com.priscilla.miappdiario.viewmodel.AuthViewModel
import kotlinx.coroutines.launch
import androidx.compose.ui.graphics.Color

@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModel: AuthViewModel = viewModel()
) {
    // Variables para email y contraseña
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Estado del usuario autenticado y errores desde el ViewModel
    val usuario by viewModel.usuario.collectAsState()
    val error by viewModel.error.collectAsState()

    // Control para ocultar el teclado
    val focusManager = LocalFocusManager.current

    // Estado para mostrar mensajes tipo snackbar
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    // Si el login es exitoso, muestra un snackbar y luego navega a la pantalla principal
    LaunchedEffect(usuario) {
        if (usuario != null) {
            focusManager.clearFocus()
            coroutineScope.launch {
                snackbarHostState.showSnackbar(
                    "Inicio de sesión exitoso",
                    duration = SnackbarDuration.Short
                )
            }
            Handler(Looper.getMainLooper()).postDelayed({
                navController.navigate(AppScreens.Entrada.route) {
                    popUpTo(AppScreens.Login.route) { inclusive = true }
                }
            }, 2000)
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Diario App",
                fontSize = 32.sp,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text("Iniciar Sesión", style = MaterialTheme.typography.headlineSmall)

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Correo electrónico") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    focusManager.clearFocus()
                    viewModel.login(email, password)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Entrar")
            }

            if (error != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = error ?: "", color = Color.Red, fontSize = 14.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = {
                navController.navigate(AppScreens.Signin.route)
            }) {
                Text("¿No tienes cuenta? Crea una aquí")
            }
        }
    }
}