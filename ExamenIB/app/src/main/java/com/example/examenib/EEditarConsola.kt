package com.example.examenib

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView

class EEditarConsola : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eeditar_consola)

        val consolaID = intent.getIntExtra("consolaID", -1)

        val botonEditarBDD = findViewById<Button>(R.id.btn_editar_consola)
        botonEditarBDD
            .setOnClickListener {
                val nombre = findViewById<EditText>(R.id.input_nombre_editar)
                val fechaLanzamiento = findViewById<EditText>(R.id.input_lanzamiento_editar)
                val descontinuado = findViewById<EditText>(R.id.input_descontinuado_editar)
                val cantidadMandos = findViewById<EditText>(R.id.id_mandos_editar)
                val precio = findViewById<EditText>(R.id.input_precio_editar)
                EBaseDeDatos.coBDatos!!.actualizarConsolaFormulario(
                    nombre.text.toString(),
                    fechaLanzamiento.text.toString(),
                    descontinuado.text.toString(),
                    cantidadMandos.text.toString().toInt(),
                    precio.text.toString().toDouble(),
                    consolaID
                )

                // Notificar al adaptador que los datos han cambiado
                actualizarListaConsolas()
            }
    }

    private fun actualizarListaConsolas() {
        val listViewConsolas = findViewById<ListView>(R.id.lv_consolas)
        val adaptador = listViewConsolas.adapter as ArrayAdapter<BConsola>?
        adaptador?.notifyDataSetChanged()
    }
}