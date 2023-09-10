package com.example.deber03

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class Historias : AppCompatActivity() {
    val historias = Historia.arregloHistorias

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historias)
        iniciarRecyclerView()


        val ver_chats = findViewById<ImageView>(R.id.ver_Chats)
        ver_chats.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    fun iniciarRecyclerView(){
        val recyclerView = findViewById<RecyclerView>(
            R.id.rv_historias
        )
        recyclerView.layoutManager = androidx.recyclerview.widget.
        GridLayoutManager(this, 2)
        recyclerView.adapter = HistoriaAdaptador(this, historias, recyclerView)


    }
}