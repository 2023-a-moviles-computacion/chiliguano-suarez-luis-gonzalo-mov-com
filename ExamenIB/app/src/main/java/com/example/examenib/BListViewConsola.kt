package com.example.examenib

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView

class BListViewConsola : AppCompatActivity() {
    val arregloConsolas = BBaseDatosMemoria.arregloConsolas
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blist_view_consola)

        val listView = findViewById<ListView>(R.id.lv_lista)
        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            arregloConsolas
        )
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()

        registerForContextMenu(listView)
    }



    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ){
        super.onCreateContextMenu(menu, v, menuInfo)
        //Llenamos las opciones del menu
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        val positionItemSeleccionado = info.position
        return when (item.itemId){
            R.id.op_editar ->{
               // "${idItemSeleccionado}"
                return true
            }
            R.id.op_eliminar ->{
                //"${idItemSeleccionado}"
               //abrirDialogo()
                return true
            }
            R.id.op_ver_juegos ->{

                return true
            }

            else -> super.onContextItemSelected(item)
        }
    }
}