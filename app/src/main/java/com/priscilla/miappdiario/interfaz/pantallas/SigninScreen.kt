package com.priscilla.miappdiario.interfaz.pantallas

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
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
fun SigninScreen(
    navController: NavHostController,
    viewModel: AuthViewModel = viewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val usuario by viewModel.usuario.collectAsState()
    val error by viewModel.error.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current

    // Navegar si ya se registró exitosamente
    LaunchedEffect(usuario) {
        if (usuario != null) {
            keyboardController?.hide()
            coroutineScope.launch {
                snackbarHostState.showSnackbar(
                    message = "¡Registro exitoso!",
                    duration = SnackbarDuration.Long
                )
            }
            navController.navigate(AppScreens.Entrada.route) {
                popUpTo(AppScreens.Signin.route) { inclusive = true }
            }
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
            Text("Crear cuenta", style = MaterialTheme.typography.headlineSmall)

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
                    keyboardController?.hide()
                    viewModel.register(email, password)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Registrarse")
            }

            if (error != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = error ?: "", color = Color.Red, fontSize = 14.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = {
                navController.navigate(AppScreens.Login.route) {
                    popUpTo(AppScreens.Login.route) { inclusive = true }
                }
            }) {
                Text("¿Ya tienes cuenta? Inicia sesión")
            }
        }
    }
}
