package com.example.examenib

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Base de datos sqlite
        EBaseDeDatos.tablaConsola = ESqliteHelperConsola(this)
        //Base de datos sqlite
        EBaseDeDatos.tablaVideojuegos = ESqliteHelperVideojuego(this)

        val btnListView = findViewById<Button>(R.id.btn_abrir)
        btnListView.setOnClickListener { irActividad(BListViewConsola::class.java) }


    }



    fun irActividad(
        clase: Class<*>
    ){
        val intent = Intent(this, clase)
        startActivity(intent)
    }
}