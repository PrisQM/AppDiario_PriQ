package com.priscilla.miappdiario

import android.os.Bundle
import android.util.Log  // ← IMPORTANTE: este import es clave
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.priscilla.miappdiario.interfaz.tema.MiAppDiarioTheme
import com.priscilla.miappdiario.navigation.NavGraph

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 👉 PRUEBA: esto debería aparecer en Logcat
        Log.d("PRUEBA_LOGCAT", "¡Este log aparece correctamente!")

        setContent {
            MiAppDiarioTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val navController = rememberNavController()
                    NavGraph(navController = navController)
                }
            }
        }
    }
}
