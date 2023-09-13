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

class EEditarVideojuego : AppCompatActivity() {
    var idVideojuegoAux = ""
    var idConsolaAux = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eeditar_videojuego)

        val videojuegoID = intent.getStringExtra("videojuegoID")
        val consolaID = intent.getStringExtra("consolaID")

        if (videojuegoID != null) {
            idVideojuegoAux = videojuegoID
        }

        if (consolaID != null) {
            idConsolaAux = consolaID
        }

        val botonEditarVideojuego = findViewById<Button>(R.id.btn_videojuego_editar)
        botonEditarVideojuego
            .setOnClickListener{
                val nombre = findViewById<EditText>(R.id.input_nombre_videojuego_editar)
                val fechaLanzamiento = findViewById<EditText>(R.id.input_lanzamiento_videojuego_editar)
                val desarrollador = findViewById<EditText>(R.id.input_desarrollador_videojuego_editar)
                val multijugadorOnline = findViewById<EditText>(R.id.input_online_videojuego_editar)
                val precio = findViewById<EditText>(R.id.input_precio_videojuego_editar)

                val videojuegoEditado = BVideojuego(
                    "",
                    nombre.text.toString(),
                    fechaLanzamiento.text.toString(),
                    desarrollador.text.toString(),
                    multijugadorOnline.text.toString(),
                    precio.text.toString().toDouble(),
                )

                // Notificar al adaptador que los datos han cambiado
                actualizarVideojuegoAFirestore(videojuegoEditado, idConsolaAux, idVideojuegoAux)
                actualizarListaVideojuegos()
            }
    }

    private fun actualizarVideojuegoAFirestore(videojuego: BVideojuego, consolaID: String, videojuegoID: String) {
        // Crea un documento sin un ID específico (Firestore generará un ID automático)
        val db = Firebase.firestore
        val consolaRef = db.collection("consolas").document(consolaID)
        val videojuegoRef = consolaRef.collection("videojuegosDeConsola").document(videojuegoID)


        val data = hashMapOf(
            "nombre" to videojuego.nombre,
            "fechaLanzamiento" to videojuego.fechaLanzamiento,
            "desarrollador" to videojuego.desarrollador,
            "multijugadorOnline" to videojuego.multijugadorOnline,
            "precioLanzamiento" to videojuego.precioLanzamiento
        )

        videojuegoRef.update(data as Map<String, Any>)
            .addOnSuccessListener {
                Toast.makeText(this, "Videojuego editado con éxito.", Toast.LENGTH_SHORT).show()
                //0finish()
            }
            .addOnFailureListener{exception ->
                Toast.makeText(this, "Error al editar el videojuego en Firestore: $exception", Toast.LENGTH_SHORT).show()

            }

    }
    private fun actualizarListaVideojuegos() {

        val listViewVideojuegos = findViewById<ListView>(R.id.lv_videojuegos)

        if (listViewVideojuegos != null) {
            val adaptador = listViewVideojuegos.adapter as ArrayAdapter<BVideojuego>?
            if (adaptador != null) {
                adaptador.notifyDataSetChanged()
            }
        } else{
            finish()
        }
    }
}