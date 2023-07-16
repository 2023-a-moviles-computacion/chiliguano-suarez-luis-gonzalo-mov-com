package com.example.examenib

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast

class BListViewVideojuegos : AppCompatActivity() {

    private lateinit var  arreglo: ArrayAdapter<BVideojuego>
    private lateinit var  videojuegos: ArrayList<BVideojuego>
    var idConsolaAux = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blist_view_videojuegos)
        EBaseDeDatos.BDatos = ESqliteHelper(this)


        val consolaID = intent.getIntExtra("consolaID", -1)

        if(consolaID != -1){
            videojuegos = obtenerVideojuegosDeConsola(consolaID)

            val listView = findViewById<ListView>(R.id.lv_videojuegos)
            arreglo = ArrayAdapter(this, android.R.layout.simple_list_item_1, videojuegos)
            listView.adapter = arreglo
            arreglo.notifyDataSetChanged()

            registerForContextMenu(listView)

            idConsolaAux = consolaID
        } else{
            Toast.makeText(this, "Error al obtener el ID de la consola", Toast.LENGTH_SHORT).show()
        }



        val botonIngresarJuego = findViewById<Button>(R.id.btn_ir_agregar_videojuego)
        botonIngresarJuego.setOnClickListener{
            irActividad(ECrudVideojuego::class.java, consolaID)
        }


    }

    override fun onResume() {
        super.onResume()

        // Obtén las consolas actualizadas desde la base de datos
        val videojuegosActualziados = obtenerVideojuegosDeConsola(idConsolaAux)

        // Borra los elementos del adaptador actual
        arreglo.clear()

        // Agrega las consolas actualizadas al adaptador
        arreglo.addAll(videojuegosActualziados)

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
        inflater.inflate(R.menu.menu_opciones_videojuego, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        val posicionSeleccionada = info.position
        val videojuegoSeleccionado = videojuegos[posicionSeleccionada]
        val idVideojuegoSeleccionado = videojuegoSeleccionado.id

        return when (item.itemId) {
            R.id.op_editar_videojuego -> {
                val intent = Intent(this, EEditarVideojuego::class.java)
                intent.putExtra("videojuegoID", idVideojuegoSeleccionado)
                startActivity(intent)
                return true
            }

            R.id.op_eliminar_videojuego -> {
                if (eliminarVideojuego(idVideojuegoSeleccionado)) {
                    Toast.makeText(this, "Elemento eliminado", Toast.LENGTH_SHORT).show()
                    videojuegos.removeAt(posicionSeleccionada)
                    arreglo.notifyDataSetChanged()
                } else {
                    Toast.makeText(this, "Error al eliminar el elemento", Toast.LENGTH_SHORT).show()
                }

                return true
            }

            else -> super.onContextItemSelected(item)
        }
    }


    private fun obtenerVideojuegosDeConsola(consolaId: Int): ArrayList<BVideojuego> {
        val dbHelperVideojuegos = ESqliteHelper(this)
        val videojuegos = dbHelperVideojuegos.obtenerVideojuegosDeConsola(consolaId)
        dbHelperVideojuegos.close()
        return videojuegos
    }
    private fun eliminarVideojuego(id: Int): Boolean {
        val dbHelper = ESqliteHelper(this)
        val conf = dbHelper.eliminarVideojuegoFormulario(id)
        dbHelper.close()
        return conf
    }



    fun irActividad(
        clase: Class<*>,
        consolaID: Int
    ){
        val intent = Intent(this, clase)
        intent.putExtra("consolaID", consolaID)
        startActivity(intent)
    }
}
