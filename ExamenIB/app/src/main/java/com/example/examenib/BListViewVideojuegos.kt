package com.example.examenib

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast

class BListViewVideojuegos : AppCompatActivity() {

    private lateinit var  arreglo: ArrayAdapter<BVideojuego>
    private lateinit var  videojuegos: ArrayList<BVideojuego>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blist_view_videojuegos)


        val consolaID = intent.getIntExtra("consolaID", -1)

        if(consolaID != -1){
            videojuegos = obtenerVideojuegosDeConsola(consolaID)

            val listView = findViewById<ListView>(R.id.lv_videojuegos)
            arreglo = ArrayAdapter(this, android.R.layout.simple_list_item_1, videojuegos)
            listView.adapter = arreglo
            arreglo.notifyDataSetChanged()
        } else{
            Toast.makeText(this, "Error al obtener el ID de la consola", Toast.LENGTH_SHORT).show()
        }


    }

    private fun obtenerVideojuegosDeConsola(consolaId: Int): ArrayList<BVideojuego> {
        val dbHelperVideojuegos = ESqliteHelper(this)
        val videojuegos = dbHelperVideojuegos.obtenerVideojuegosDeConsola(consolaId)
        dbHelperVideojuegos.close()
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
