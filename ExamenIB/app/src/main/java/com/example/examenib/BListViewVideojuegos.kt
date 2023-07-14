package com.example.examenib

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView

class BListViewVideojuegos : AppCompatActivity() {

    private lateinit var  arreglo: ArrayAdapter<BVideojuegos>
    private lateinit var  videojuegos: ArrayList<BVideojuegos>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blist_view_videojuegos)

        // Obtener la lista de videojuegos relacionados a la consola seleccionada
        videojuegos = obtenerVideojuegosPorConsolaId()

        // Configurar el adaptador para la lista de videojuegos
        val listView = findViewById<ListView>(R.id.lv_videojuegos)
        arreglo = ArrayAdapter(
            this, //contexto
            android.R.layout.simple_list_item_1,
            videojuegos
        )

        listView.adapter = arreglo
        arreglo.notifyDataSetChanged()

        registerForContextMenu(listView)
    }

    private fun obtenerVideojuegosPorConsolaId(): ArrayList<BVideojuegos> {
        val dbHelper = ESqliteHelperVideojuego(this)
        // Aquí obtienes los videojuegos según el ID de la consola recibido en el intent
        val consolaId = intent.getIntExtra("consolaId", 1)
        val videojuegos = dbHelper.obtenerVideojuegosDeConsola(consolaId)
        dbHelper.close()
        return videojuegos
    }

    fun irActividad(
        clase: Class<*>,
        consolaID: Int
    ){
        val intent = Intent(this, clase)
        intent.putExtra("CONSOLA_ID", consolaID)
        startActivity(intent)
    }
}