package com.example.examen2b

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
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class BListViewVideojuegos : AppCompatActivity() {

    var query: Query? = null
    //private lateinit var db: FirebaseFirestore

    var  arreglo: ArrayList<BVideojuego> = arrayListOf()

    private lateinit var  adaptador: ArrayAdapter<BVideojuego>

    var idConsolaAux = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blist_view_videojuegos)
        //EBaseDeDatos.BDatos = ESqliteHelper(this)


        var consolaID = intent.getStringExtra("consolaID")

        if(consolaID != null){

            val listView = findViewById<ListView>(R.id.lv_videojuegos)
            adaptador = ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                arreglo
            )

            listView.adapter = adaptador
            registerForContextMenu(listView)

            obtenerVideojuegosDeConsola(consolaID!!, adaptador)
            adaptador.notifyDataSetChanged()


        } else{
            Toast.makeText(this, "Error al obtener el ID de la consola", Toast.LENGTH_SHORT).show()
        }

        if (consolaID != null) {
            idConsolaAux = consolaID
        }


        val botonIngresarJuego = findViewById<Button>(R.id.btn_ir_agregar_videojuego)
        botonIngresarJuego.setOnClickListener{
            irActividad(ECrudVideojuego::class.java, consolaID!!)
        }


    }

    override fun onResume() {
        super.onResume()
        adaptador.clear()
        obtenerVideojuegosDeConsola(idConsolaAux, adaptador)
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
        val videojuegoSeleccionado = arreglo[posicionSeleccionada]
        val idVideojuegoSeleccionado = videojuegoSeleccionado.nombre

        return when (item.itemId) {
            R.id.op_editar_videojuego -> {
                val intent = Intent(this, EEditarVideojuego::class.java)
                intent.putExtra("videojuegoID", idVideojuegoSeleccionado)
                startActivity(intent)
                return true
            }

            /*R.id.op_eliminar_videojuego -> {
                if (eliminarVideojuego(idVideojuegoSeleccionado)) {
                    Toast.makeText(this, "Elemento eliminado", Toast.LENGTH_SHORT).show()
                    videojuegos.removeAt(posicionSeleccionada)
                    arreglo.notifyDataSetChanged()
                } else {
                    Toast.makeText(this, "Error al eliminar el elemento", Toast.LENGTH_SHORT).show()
                }

                return true
            }*/

            else -> super.onContextItemSelected(item)
        }
    }


    private fun obtenerVideojuegosDeConsola(
        consolaId: String,
        adaptador: ArrayAdapter<BVideojuego>
    ) {
        //val dbHelperVideojuegos = ESqliteHelper(this)
        val db = Firebase.firestore
        val videojuegosRef =
            db.collection("consolas").document(consolaId).collection("videojuegosDeConsola")

        videojuegosRef
            .orderBy("nombre", Query.Direction.ASCENDING)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val nuevosElementos = ArrayList<BVideojuego>() //Arreglo temporal

                for (videojuego in querySnapshot) {
                    val nuevoVideojuego = BVideojuego(
                        videojuego.data.get("nombre") as String,
                        videojuego.data?.get("fechaLanzamiento") as String?,
                        videojuego.data?.get("desarrollador") as String?,
                        videojuego.data?.get("multijugadorOnline") as String?,
                        videojuego.data?.get("precioLanzamiento") as Double?
                    )
                    nuevosElementos.add(nuevoVideojuego)
                }

                adaptador.clear()
                arreglo = nuevosElementos

                // Agrega los nuevos elementos al arreglo y al adaptador
                adaptador.addAll(nuevosElementos)
                adaptador.notifyDataSetChanged()

            }
            .addOnFailureListener { exception ->
                Toast.makeText(
                    this,
                    "Error al obtener las consolas desde Firestore: $exception",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }
   /* private fun eliminarVideojuego(id: Int): Boolean {


        return
    }*/



    fun irActividad(
        clase: Class<*>,
        consolaID: String
    ){
        val intent = Intent(this, clase)
        intent.putExtra("consolaID", consolaID)
        startActivity(intent)
    }
}