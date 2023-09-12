package com.example.examen2b

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.*
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class BListViewConsola : AppCompatActivity() {


    var query: Query? = null
    //private lateinit var db: FirebaseFirestore

    var  arreglo: ArrayList<BConsola> = arrayListOf()

    private lateinit var  adaptador: ArrayAdapter<BConsola>





    //var idItemSeleccionado = 0

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blist_view_consola)



        val listView = findViewById<ListView>(R.id.lv_consolas)
        adaptador = ArrayAdapter(
            this, //contexto
            android.R.layout.simple_list_item_1,
            arreglo
        )

        obtenerConsolasDesdeFirestore(adaptador)


        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()

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



        // Borra los elementos del adaptador actual
        adaptador.clear()
        obtenerConsolasDesdeFirestore(adaptador)

        // Agrega las consolas actualizadas al adaptador
        //arreglo.addAll(consolasActualizadas)

        // Notifica al adaptador que los datos han cambiado
        //adaptador.notifyDataSetChanged()
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
        val consolaSeleccionada = arreglo[posicionSeleccionada]
        val idSeleccionado = consolaSeleccionada.nombre

        return when (item.itemId){
            R.id.op_editar ->{
                val intent = Intent(this, EEditarConsola::class.java)
                intent.putExtra("consolaID", idSeleccionado)
                startActivity(intent)

                return true
            }
            /*R.id.op_eliminar_ ->{
                if(eliminarConsola(idSeleccionado)){
                    Toast.makeText(this, "Elemento eliminado", Toast.LENGTH_SHORT).show()
                    consolas.removeAt(posicionSeleccionada)
                    arreglo.notifyDataSetChanged()
                } else
                {
                    Toast.makeText(this, "Error al eliminar el elemento", Toast.LENGTH_SHORT).show()
                }

                return true
            }*/
            R.id.op_ver_juegos ->{
                val intent = Intent(this, BListViewVideojuegos::class.java)
                intent.putExtra("consolaID", idSeleccionado)
                startActivity(intent)
                return true
            }

            else -> super.onContextItemSelected(item)
        }

    }

    /*private fun obtenerConsolasDesdeFirestore(
        adaptador: ArrayAdapter<BConsola>
    ){
        val db = Firebase.firestore
        val consolasRefUnico = db.collection("consolas")
        limpiarArreglo()
        adaptador.notifyDataSetChanged()

        consolasRefUnico
            .orderBy("nombre", Query.Direction.ASCENDING)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val nuevosElementos = ArrayList<BConsola>() // Arreglo temporal

                for (consola in querySnapshot) {
                    val nuevaConsola = BConsola(
                        consola.data?.get("nombre") as String,
                        consola.data?.get("fechaLanzamiento") as String?,
                        consola.data?.get("descontinuado") as String?,
                        consola.data?.get("cantidadMandos") as Long?,
                        consola.data?.get("precioLanzamiento") as Double?,
                        consola.data?.get("listaVideojuegos") as ArrayList<BVideojuego>?,
                    )
                    nuevosElementos.add(nuevaConsola)
                }

                // Borra los elementos existentes en el adaptador y en el arreglo
                //adaptador.clear()
                //arreglo.clear()

                // Agrega los nuevos elementos al arreglo y al adaptador
                arreglo.addAll(nuevosElementos)
                adaptador.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Error al obtener las consolas desde Firestore: $exception", Toast.LENGTH_SHORT).show()
            }

    }*/

    private fun obtenerConsolasDesdeFirestore(
        adaptador: ArrayAdapter<BConsola>
    ){
        val db = Firebase.firestore
        val consolasRefUnico = db.collection("consolas")
        limpiarArreglo()
        // Borra los elementos existentes en el adaptador y en el arreglo
        adaptador.clear()
        adaptador.notifyDataSetChanged()

        consolasRefUnico
            .orderBy("nombre", Query.Direction.ASCENDING)
            .get()
            .addOnSuccessListener {
                limpiarArreglo()
                for (consola in it) {
                    anadirArregloConsola(consola)
                }

                    adaptador.notifyDataSetChanged()
            }

            .addOnFailureListener { exception ->
                Toast.makeText(this, "Error al obtener las consolas desde Firestore: $exception", Toast.LENGTH_SHORT).show()
            }

    }

   /* private fun eliminarConsola(id: Int): Boolean {
        val dbHelper = ESqliteHelper(this)
        val conf = dbHelper.eliminarConsolaFormulario(id)
        dbHelper.close()
        return conf
    }*/



    fun limpiarArreglo(){ arreglo.clear() }

    fun anadirArregloConsola(consola: QueryDocumentSnapshot)
    {
        //ciudad.id
        val nuevaConsola = BConsola(
            consola.data?.get("nombre") as String,
            consola.data?.get("fechaLanzamiento") as String?,
            consola.data?.get("descontinuado") as String?,
            consola.data?.get("cantidadMandos") as Long?,
            consola.data?.get("precioLanzamiento") as Double?,
            consola.data?.get("listaVideojuegos") as ArrayList<BVideojuego>?,
        )
        arreglo.add(nuevaConsola)
    }





    fun irActividad(
        clase: Class<*>
    ){
        val intent = Intent(this, clase)
        startActivity(intent)
    }
}