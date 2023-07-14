package com.example.examenib

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView

class BListViewConsola : AppCompatActivity() {
    private lateinit var  arreglo: ArrayAdapter<BConsola>
    private lateinit var  consolas: ArrayList<BConsola>

    var idItemSeleccionado = 0
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blist_view_consola)

        //Obtener las consolas desde la base de datos
        consolas = obtenerConsolasDesdeLaBaseDeDatos()



        val listView = findViewById<ListView>(R.id.lv_consolas)
        arreglo = ArrayAdapter(
            this, //contexto
            android.R.layout.simple_list_item_1,
            consolas
        )


        listView.adapter = arreglo
        arreglo.notifyDataSetChanged()
        registerForContextMenu(listView)


        val botonListView = findViewById<Button>(R.id.btn_ir_crear_consola)
        botonListView
            .setOnClickListener{
                irActividad(ECrudConsola::class.java)
            }
    }

    override fun onResume() {
        super.onResume()

        // Obtén las consolas actualizadas desde la base de datos
        val consolasActualizadas = obtenerConsolasDesdeLaBaseDeDatos()

        // Borra los elementos del adaptador actual
        arreglo.clear()

        // Agrega las consolas actualizadas al adaptador
        arreglo.addAll(consolasActualizadas)

        // Notifica al adaptador que los datos han cambiado
        arreglo.notifyDataSetChanged()
    }

    private fun obtenerConsolasDesdeLaBaseDeDatos(): ArrayList<BConsola> {
        val dbHelper = ESqliteHelperConsola(this)
        val consolas = dbHelper.obtenerTodasLasConsolas()
        dbHelper.close()
        return consolas
    }



    fun irActividad(
        clase: Class<*>
    ){
        val intent = Intent(this, clase)
        startActivity(intent)
    }
}