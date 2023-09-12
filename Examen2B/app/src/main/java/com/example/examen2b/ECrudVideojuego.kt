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

class ECrudVideojuego : AppCompatActivity() {
    var idConsolaAux = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ecrud_videojuego)


        // Obtener el consolaId de los extras del intent
        val consolaId = intent.getStringExtra("consolaID")

        if (consolaId != null) {
            if (consolaId.isEmpty()){
                Toast.makeText(this, "Error: ID de la consola no proporcionado.", Toast.LENGTH_SHORT).show()
                finish()
                return
            }
            idConsolaAux = consolaId
        }

        val botonCrearVideojuego = findViewById<Button>(R.id.btn_agregar_videojuego)
        botonCrearVideojuego
            .setOnClickListener {
                val nombre = findViewById<EditText>(R.id.input_nombre_videojuego)
                val fechaLanzamiento = findViewById<EditText>(R.id.input_lanzamiento_videojuego)
                val desarrollador = findViewById<EditText>(R.id.input_desarrollador_videojuego)
                val multijugadorOnline = findViewById<EditText>(R.id.input_online_videojuego)
                val precio = findViewById<EditText>(R.id.input_precio_videojuego)

                val nuevoVideojuego = BVideojuego(
                    nombre.text.toString(),
                    fechaLanzamiento.text.toString(),
                    desarrollador.text.toString(),
                    multijugadorOnline.text.toString(),
                    precio.text.toString().toDouble()
                )

                // Notificar al adaptador que los datos han cambiado
                if (consolaId != null) {
                    agregarVideojuegoAFirestore(nuevoVideojuego, consolaId)
                }
                actualizarListaVideojuegos()
            }


    }


    private fun agregarVideojuegoAFirestore(videojuego: BVideojuego, consolaID:String) {
        val db = Firebase.firestore
        val consolasRef = db.collection("consolas")

        val data = hashMapOf(
            "nombre" to videojuego.nombre,
            "fechaLanzamiento" to videojuego.fechaLanzamiento,
            "desarrollador" to videojuego.desarrollador,
            "multijugadorOnline" to videojuego.multijugadorOnline,
            "precioLanzamiento" to videojuego.precioLanzamiento
        )

        consolasRef.document(consolaID)
            .collection("videojuegosDeConsola")
            .document(videojuego.nombre)
            .set(data)
            .addOnSuccessListener {
                Toast.makeText(this, "Videojuego creado con éxito.", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error al crear el videojuego: $e", Toast.LENGTH_SHORT).show()
            }
    }



    private fun actualizarListaVideojuegos() {
        val listViewVideojuegos = findViewById<ListView>(R.id.lv_videojuegos)

        if (listViewVideojuegos != null ) {
            val adaptador = listViewVideojuegos.adapter as ArrayAdapter<BVideojuego>?
            if (adaptador != null) {
                adaptador.notifyDataSetChanged()
            }

        } else {
            finish()
        }


    }
}