package com.example.examenib

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.*

class BListViewConsola : AppCompatActivity() {
    private lateinit var  arreglo: ArrayAdapter<BConsola>
    private lateinit var  consolas: ArrayList<BConsola>



    //var idItemSeleccionado = 0

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blist_view_consola)
        EBaseDeDatos.coBDatos = ESqliteHelper(this)

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

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)

        val inflater = menuInflater
        inflater.inflate(R.menu.menu_opciones_consolas, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        val posicionSeleccionada = info.position
        val consolaSeleccionada = consolas[posicionSeleccionada]
        val idSeleccionado = consolaSeleccionada.id

        return when (item.itemId){
            R.id.op_editar ->{
                val intent = Intent(this, EEditarConsola::class.java)
                intent.putExtra("consolaID", idSeleccionado)
                startActivity(intent)

                return true
            }
            R.id.op_eliminar_ ->{
             if(eliminarConsola(idSeleccionado)){
                 Toast.makeText(this, "Elemento eliminado", Toast.LENGTH_SHORT).show()
                 consolas.removeAt(posicionSeleccionada)
                 arreglo.notifyDataSetChanged()
             } else
             {
                 Toast.makeText(this, "Error al eliminar el elemento", Toast.LENGTH_SHORT).show()
             }

                return true
            }
            R.id.op_ver_juegos ->{
                val intent = Intent(this, BListViewVideojuegos::class.java)
                intent.putExtra("consolaID", idSeleccionado)
                startActivity(intent)
                return true
            }

            else -> super.onContextItemSelected(item)
        }

    }

    private fun obtenerConsolasDesdeLaBaseDeDatos(): ArrayList<BConsola> {
        val dbHelper = ESqliteHelper(this)
        val consolas = dbHelper.obtenerTodasLasConsolas()
        dbHelper.close()
        return consolas
    }

    private fun eliminarConsola(id: Int): Boolean {
        val dbHelper = ESqliteHelper(this)
        val conf = dbHelper.eliminarConsolaFormulario(id)
        dbHelper.close()
        return conf
    }




    fun irActividad(
        clase: Class<*>
    ){
        val intent = Intent(this, clase)
        startActivity(intent)
    }
}