package com.example.examenib

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ESqliteHelperVideojuego(
    contexto: Context?,
) : SQLiteOpenHelper(
    contexto,
    "moviles",
    null,
    1
) {
    override fun onCreate(db: SQLiteDatabase?) {
        val scriptSQLCrearTablaVideojuego =
            """
                CREATE TABLE VIDEOJUEGO(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre VARCHAR(50),
                fechaLanzamiento VARCHAR(50),
                desarrollador VARCHAR(50),
                multijugadorOnline VARCHAR(10),
                precioLanzamiento DOUBLE,
                consolaID INTEGER,
                FOREIGN KEY (consolaID) REFERENCES CONSOLA(id) ON DELETE CASCADE 
                )
            
            """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaVideojuego)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

    fun crearVideojuego(
        nombre: String,
        fechaLanzamiento: String,
        desarrollador: String,
        multijugadorOnline: String,
        precioLanzamiento: Double,
        consolaID: Int
    ): Boolean{
        val basedatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("nombre", nombre)
        valoresAGuardar.put("fechaLanzamiento", fechaLanzamiento)
        valoresAGuardar.put("desarrollador", desarrollador)
        valoresAGuardar.put("multijugadorOnline", multijugadorOnline)
        valoresAGuardar.put("precioLanzamiento", precioLanzamiento)
        valoresAGuardar.put("consolaID", consolaID)
        val resultadoGuardar = basedatosEscritura
            .insert(
                "VIDEOJUEGO",
                null,
                valoresAGuardar
            )
        basedatosEscritura.close()
        return if (resultadoGuardar.toInt() == -1) false else true

    }

    fun eliminarVideojuegoFormulario(id: Int): Boolean {
        val conexionEscritura = writableDatabase
        val parametrosConsultaDelete = arrayOf(id.toString())
        val resultadoEliminacion = conexionEscritura
            .delete(
                "VIDEOJUEGO",
                "id=?",
                parametrosConsultaDelete
            )
        conexionEscritura.close()
        return if (resultadoEliminacion.toInt() == -1) false else true
    }

    fun actualizarVideojuegoFormulario(
        nombre: String,
        fechaLanzamiento: String,
        desarrollador: String,
        multijugadorOnliine: String,
        precioLanzamiento: Double,
        id: Int,
    ): Boolean{
        val conexionEscritura = writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("nombre", nombre)
        valoresAActualizar.put("fechaLanzamiento", fechaLanzamiento)
        valoresAActualizar.put("desarrollador", desarrollador)
        valoresAActualizar.put("multijugadorOnline", multijugadorOnliine)
        valoresAActualizar.put("precioLanzamiento", precioLanzamiento)
        val parametrosConsultaActualizar = arrayOf(id.toString())
        val resultadosActualizacion = conexionEscritura
            .update(
                "VIDEOJUEGO",
                valoresAActualizar,
                "id=?",
                parametrosConsultaActualizar
            )
        conexionEscritura.close()
        return if (resultadosActualizacion.toInt() == -1) false else true
    }

    fun consultarVideojuegoPorId(id:Int):BVideojuegos{
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = """
            SELECT * FROM VIDEOJUEGO WHERE ID = ?
            """.trimIndent()
        val parametrosConsultaLectura = arrayOf(id.toString())
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultaLectura,
            parametrosConsultaLectura,
        )

        val existeVideojuego = resultadoConsultaLectura.moveToFirst()
        val vieojuegoEncontrado = BVideojuegos(0, "", "", "", "", 0.0, 0)
        val arreglo = arrayListOf<BVideojuegos>()
        if(existeVideojuego){
            do{
                val id = resultadoConsultaLectura.getInt(0)
                val nombre = resultadoConsultaLectura.getString(1)
                val fechaLanzamiento = resultadoConsultaLectura.getString(2)
                val desarrollador = resultadoConsultaLectura.getInt(3)
                val multijugadorOnline = resultadoConsultaLectura.getInt(4)
                val precioLanzamiento = resultadoConsultaLectura.getDouble(5)
                if(id != null){
                    vieojuegoEncontrado.id = id
                    vieojuegoEncontrado.nombre = nombre.toString()
                    vieojuegoEncontrado.fechaLanzamiento = fechaLanzamiento.toString()
                    vieojuegoEncontrado.desarrollador = desarrollador.toString()
                    vieojuegoEncontrado.multijugadorOnline = multijugadorOnline.toString()
                    vieojuegoEncontrado.precioLanzamiento = precioLanzamiento

                }

            } while (resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return vieojuegoEncontrado
    }

    fun obtenerVideojuegosDeConsola(consolaId: Int): ArrayList<BVideojuegos> {
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = """
        SELECT * FROM VIDEOJUEGO WHERE consolaID = ?
    """.trimIndent()
        val parametrosConsultaLectura = arrayOf(consolaId.toString())
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(scriptConsultaLectura, parametrosConsultaLectura)

        val arregloVideojuegos = ArrayList<BVideojuegos>()

        if (resultadoConsultaLectura.moveToFirst()) {
            do {
                val id = resultadoConsultaLectura.getInt(0)
                val nombre = resultadoConsultaLectura.getString(1)
                val lanzamiento = resultadoConsultaLectura.getString(2)
                val desarrollador = resultadoConsultaLectura.getString(3)
                val multijugadorOnline = resultadoConsultaLectura.getString(4)
                val precioLanzamiento = resultadoConsultaLectura.getDouble(5)

                val videojuego = BVideojuegos(id, nombre, lanzamiento, desarrollador, multijugadorOnline, precioLanzamiento, consolaId)
                arregloVideojuegos.add(videojuego)
            } while (resultadoConsultaLectura.moveToNext())
        }

        resultadoConsultaLectura.close()
        baseDatosLectura.close()

        return arregloVideojuegos
    }

}