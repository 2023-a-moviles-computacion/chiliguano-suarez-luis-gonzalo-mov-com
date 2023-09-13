package com.example.examen2b

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class EEditarConsola : AppCompatActivity() {
    var idConsolaAux = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eeditar_consola)

        //Obtener el consolaID de los extras del intent
        val consolaId = intent.getStringExtra("consolaID")

        if (consolaId != null) {
            if (consolaId.isEmpty()){
                Toast.makeText(this, "Error: ID de la consola no proporcionado.", Toast.LENGTH_SHORT).show()
                finish()
                return
            }
            idConsolaAux = consolaId
        }

        val botonEditarConsola = findViewById<Button>(R.id.btn_editar_consola)
        botonEditarConsola
            .setOnClickListener {
                val nuevoNombre = findViewById<EditText>(R.id.input_nombre_editar)
                val nuevaFechaLanzamiento = findViewById<EditText>(R.id.input_lanzamiento_editar)
                val nuevoDescontinuado = findViewById<EditText>(R.id.input_descontinuado_editar)
                val nuevoCantidadMandos = findViewById<EditText>(R.id.id_mandos_editar)
                val nuevoPrecio = findViewById<EditText>(R.id.input_precio_editar)

                val consolaEditada = BConsola(
                    "",
                    nuevoNombre.text.toString(),
                    nuevaFechaLanzamiento.text.toString(),
                    nuevoDescontinuado.text.toString(),
                    nuevoCantidadMandos.text.toString().toLong(),
                    nuevoPrecio.text.toString().toDouble()
                )

                // Notificar al adaptador que los datos han cambiado
                actualizarConsolaAFirestore(consolaEditada, idConsolaAux)
                actualizarListaConsolas()
            }
    }

    private fun actualizarConsolaAFirestore(consola: BConsola, consolaID: String) {
        // Crea un documento sin un ID específico (Firestore generará un ID automático)
        val db = Firebase.firestore
        val consolas = db.collection("consolas")

        val data = hashMapOf(
            "nombre" to consola.nombre,
            "fechaLanzamiento" to consola.fechaLanzamiento,
            "descontinuado" to consola.descontinuado,
            "cantidadMandos" to consola.cantidadMandos,
            "precioLanzamiento" to consola.precioLanzamiento
        )

        consolas.document(consolaID).update(data as Map<String, Any>)
            .addOnSuccessListener {
                Toast.makeText(this, "Consola editada con éxito.", Toast.LENGTH_SHORT).show()
                //0finish()
            }
            .addOnFailureListener{exception ->
                Toast.makeText(this, "Error al editar la consola en Firestore: $exception", Toast.LENGTH_SHORT).show()

            }

    }

    private fun actualizarListaConsolas() {

        val listViewConsolas = findViewById<ListView>(R.id.lv_consolas)

        if (listViewConsolas != null) {
            val adaptador = listViewConsolas.adapter as ArrayAdapter<BConsola>?
            if (adaptador != null) {
                adaptador.notifyDataSetChanged()
            }
        } else{
            finish()
        }
    }
}