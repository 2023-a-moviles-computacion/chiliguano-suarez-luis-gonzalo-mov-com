package com.example.examenib

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView

class ECrudConsola : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ecrud_consola)
        EBaseDeDatos.coBDatos = ESqliteHelper(this)

        val botonCrearBDD = findViewById<Button>(R.id.btn_crear_consola)
        botonCrearBDD
            .setOnClickListener {
                val nombre = findViewById<EditText>(R.id.input_nombre)
                val fechaLanzamiento = findViewById<EditText>(R.id.input_lanzamiento)
                val descontinuado = findViewById<EditText>(R.id.input_descontinuado)
                val cantidadMandos = findViewById<EditText>(R.id.input_mandos)
                val precio = findViewById<EditText>(R.id.input_precio)
                EBaseDeDatos.coBDatos!!.crearConsola(
                    nombre.text.toString(),
                    fechaLanzamiento.text.toString(),
                    descontinuado.text.toString(),
                    cantidadMandos.text.toString().toInt(),
                    precio.text.toString().toDouble()
                )

                // Notificar al adaptador que los datos han cambiado
                actualizarListaConsolas()
            }



    }

    private fun actualizarListaConsolas() {
        val listViewConsolas = findViewById<ListView>(R.id.lv_consolas)
        val adaptador = listViewConsolas.adapter as ArrayAdapter<BConsola>?
        if (adaptador != null) {
            adaptador.notifyDataSetChanged()
        }
    }
}