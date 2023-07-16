package com.example.examenib

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView

class ECrudVideojuego : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ecrud_videojuego)
        EBaseDeDatos.BDatos = ESqliteHelper(this)


        // Obtener el consolaId de los extras del intent
        val consolaId = intent.getIntExtra("consolaID", -1)

        val botonCrearBDD = findViewById<Button>(R.id.btn_agregar_videojuego)
        botonCrearBDD
            .setOnClickListener {
                val nombre = findViewById<EditText>(R.id.input_nombre_videojuego)
                val fechaLanzamiento = findViewById<EditText>(R.id.input_lanzamiento_videojuego)
                val desarrollador = findViewById<EditText>(R.id.input_desarrollador_videojuego)
                val multijugadorOnline = findViewById<EditText>(R.id.input_online_videojuego)
                val precio = findViewById<EditText>(R.id.input_precio_videojuego)
                EBaseDeDatos.BDatos!!.crearVideojuego(
                    nombre.text.toString(),
                    fechaLanzamiento.text.toString(),
                    desarrollador.text.toString(),
                    multijugadorOnline.text.toString(),
                    precio.text.toString().toDouble(),
                    consolaId.toString().toInt()
                )

                // Notificar al adaptador que los datos han cambiado
                actualizarListaVideojuegos()
            }


    }

    private fun actualizarListaVideojuegos() {
        val listViewVideojuegos = findViewById<ListView>(R.id.lv_videojuegos)
        val adaptador = listViewVideojuegos.adapter as ArrayAdapter<BVideojuego>?
        if (adaptador != null ) {
            adaptador.notifyDataSetChanged()

        } else {
            finish()
        }


    }
}