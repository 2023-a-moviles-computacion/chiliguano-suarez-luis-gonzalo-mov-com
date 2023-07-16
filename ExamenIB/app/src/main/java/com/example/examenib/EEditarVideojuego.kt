package com.example.examenib

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView

class EEditarVideojuego : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eeditar_videojuego)
        EBaseDeDatos.BDatos = ESqliteHelper(this)

        val videojuegoID = intent.getIntExtra("videojuegoID", -1)

        val botonEditarBD = findViewById<Button>(R.id.btn_videojuego_editar)
        botonEditarBD
            .setOnClickListener{
                val nombre = findViewById<EditText>(R.id.input_nombre_videojuego_editar)
                val fechaLanzamiento = findViewById<EditText>(R.id.input_lanzamiento_videojuego_editar)
                val desarrollador = findViewById<EditText>(R.id.input_desarrollador_videojuego_editar)
                val multijugadorOnline = findViewById<EditText>(R.id.input_online_videojuego_editar)
                val precio = findViewById<EditText>(R.id.input_precio_videojuego_editar)
                EBaseDeDatos.BDatos!!.actualizarVideojuegoFormulario(
                    nombre.text.toString(),
                    fechaLanzamiento.text.toString(),
                    desarrollador.text.toString(),
                    multijugadorOnline.text.toString(),
                    precio.text.toString().toDouble(),
                    videojuegoID
                )

                // Notificar al adaptador que los datos han cambiado
                actualizarListaVideojuegos()
            }
    }

    private fun actualizarListaVideojuegos() {
        val listViewVideojuegos = findViewById<ListView>(R.id.lv_videojuegos)
        val adaptador = listViewVideojuegos.adapter as ArrayAdapter<BVideojuego>?
        if (adaptador != null) {
            adaptador.notifyDataSetChanged()
        }
    }
}