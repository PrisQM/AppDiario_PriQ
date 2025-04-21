package com.priscilla.miappdiario.data

import com.google.firebase.firestore.FirebaseFirestore
import com.priscilla.miappdiario.model.EntradaDiaria
import kotlinx.coroutines.tasks.await
import android.util.Log

class EntradaRepository {

    // Referencia a la base de datos de Firestore
    private val db = FirebaseFirestore.getInstance()

    // Guarda o sobrescribe una entrada utilizando la fecha como ID del documento
    suspend fun guardarEntrada(entrada: EntradaDiaria) {
        try {
            val idFecha = entrada.fecha
            db.collection("entradas")
                .document(idFecha)
                .set(entrada)
                .await()
            Log.d("GuardarEntrada", "Entrada guardada correctamente: $entrada")
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("GuardarEntrada", "Error al guardar entrada: ${e.message}")
        }
    }

    // Obtiene todas las entradas desde la colección "entradas"
    suspend fun obtenerEntradas(): List<EntradaDiaria> {
        return try {
            val snapshot = db.collection("entradas").get().await()
            snapshot.toObjects(EntradaDiaria::class.java)

        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    //obtiene las entradas por fecha
    suspend fun obtenerEntradaPorFecha(fecha: String): EntradaDiaria? {
        return try {
            val doc = db.collection("entradas").document(fecha).get().await()
            doc.toObject(EntradaDiaria::class.java)
        } catch (e: Exception) {
            null
        }
    }

    // Elimina una entrada específica por ID (fecha)
    suspend fun eliminarEntrada(id: String) {
        try {
            db.collection("entradas").document(id).delete().await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // Edita una entrada específica (sobrescribe usando la misma fecha como ID)
    suspend fun editarEntrada(entrada: EntradaDiaria) {
        try {
            val ref = db.collection("entradas").document(entrada.fecha)
            ref.set(entrada).await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
