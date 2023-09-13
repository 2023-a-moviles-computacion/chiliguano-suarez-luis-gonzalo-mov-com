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

class ECrudConsola : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ecrud_consola)

        val botonCrearConsola = findViewById<Button>(R.id.btn_crear_consola)
        botonCrearConsola
            .setOnClickListener {

                val nombre = findViewById<EditText>(R.id.input_nombre)
                val fechaLanzamiento = findViewById<EditText>(R.id.input_lanzamiento)
                val descontinuado = findViewById<EditText>(R.id.input_descontinuado)
                val cantidadMandos = findViewById<EditText>(R.id.input_mandos)
                val precio = findViewById<EditText>(R.id.input_precio)


                val nuevaConsola = BConsola(
                    "",
                    nombre.text.toString(),
                    fechaLanzamiento.text.toString(),
                    descontinuado.text.toString(),
                    cantidadMandos.text.toString().toLong(),
                    precio.text.toString().toDouble(),
                )

                agregarConsolaAFirestore(nuevaConsola)
                actualizarListaConsolas()

            }



    }

    private fun agregarConsolaAFirestore(consola: BConsola) {
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
        consolas.add(data)
            .addOnSuccessListener {
                Toast.makeText(this, "Consola creada con éxito.", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener { exception ->
                Toast.makeText(
                    this,
                    "Error al agregar la consola en Firestore: $exception",
                    Toast.LENGTH_SHORT
                ).show()
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