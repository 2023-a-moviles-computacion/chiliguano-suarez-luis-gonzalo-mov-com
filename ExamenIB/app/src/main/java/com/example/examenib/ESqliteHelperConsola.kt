package com.example.examenib

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ESqliteHelperConsola(
    contexto: Context?,
    ) : SQLiteOpenHelper(
    contexto,
    "moviles",
    null,
    1
    ) {
    override fun onCreate(db: SQLiteDatabase?) {
        val scriptSQLCrearTablaConsola =
            """
                CREATE TABLE CONSOLA(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre VARCHAR(50),
                fechaLanzamiento VARCHAR(50),
                descontinuado VARCHAR(10),
                cantidadMandos INTEGER,
                precioLanzamiento DOUBLE
                )
            
            """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaConsola)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

    fun crearConsola(
        nombre: String,
        fechaLanzamiento: String,
        descontinuado: String,
        cantidadMandos: Int,
        precioLanzamiento: Double

    ): Boolean{
        val basedatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("nombre", nombre)
        valoresAGuardar.put("fechaLanzamiento", fechaLanzamiento)
        valoresAGuardar.put("descontinuado", descontinuado)
        valoresAGuardar.put("cantidadMandos", cantidadMandos)
        valoresAGuardar.put("precioLanzamiento", precioLanzamiento)
        val resultadoGuardar = basedatosEscritura
            .insert(
                "CONSOLA",
                null,
                valoresAGuardar
            )
        basedatosEscritura.close()
        return if (resultadoGuardar.toInt() == -1) false else true

    }

    fun eliminarConsolaFormulario(id: Int): Boolean {
        val conexionEscritura = writableDatabase
        val parametrosConsultaDelete = arrayOf(id.toString())
        val resultadoEliminacion = conexionEscritura
            .delete(
                "CONSOLA",
                "id=?",
                parametrosConsultaDelete
            )
        conexionEscritura.close()
        return if (resultadoEliminacion.toInt() == -1) false else true
    }

    fun actualizarConsolaFormulario(
        nombre: String,
        fechaLanzamiento: String,
        descontinuado: String,
        cantidadMandos: Int,
        precioLanzamiento: Double,
        id: Int,
    ): Boolean{
        val conexionEscritura = writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("nombre", nombre)
        valoresAActualizar.put("fechaLanzamiento", fechaLanzamiento)
        valoresAActualizar.put("descontinuado", descontinuado)
        valoresAActualizar.put("cantidadMandos", cantidadMandos)
        valoresAActualizar.put("precioLanzamiento", precioLanzamiento)
        val parametrosConsultaActualizar = arrayOf(id.toString())
        val resultadosActualizacion = conexionEscritura
            .update(
                "CONSOLA",
                valoresAActualizar,
                "id=?",
                parametrosConsultaActualizar
            )
        conexionEscritura.close()
        return if (resultadosActualizacion.toInt() == -1) false else true
    }

    fun consultarConsolaPorId(id:Int):BConsola{
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = """
            SELECT * FROM CONSOLA WHERE ID = ?
            """.trimIndent()
        val parametrosConsultaLectura = arrayOf(id.toString())
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultaLectura,
            parametrosConsultaLectura,
        )

        val existeConsola = resultadoConsultaLectura.moveToFirst()
        val consolaEncontrada = BConsola(0, "", "", "", 0, 0.0)
        val arreglo = arrayListOf<BConsola>()
        if(existeConsola){
            do{
                val id = resultadoConsultaLectura.getInt(0)
                val nombre = resultadoConsultaLectura.getString(1)
                val fechaLanzamiento = resultadoConsultaLectura.getString(2)
                val descontinuado = resultadoConsultaLectura.getInt(3)
                val cantidadMandos = resultadoConsultaLectura.getInt(4)
                val precioLanzamiento = resultadoConsultaLectura.getDouble(5)
                if(id != null){
                    consolaEncontrada.id = id
                    consolaEncontrada.nombre = nombre.toString()
                    consolaEncontrada.fechaLanzamiento = fechaLanzamiento.toString()
                    consolaEncontrada.descontinuado = descontinuado.toString()
                    consolaEncontrada.cantidadMandos = cantidadMandos
                    consolaEncontrada.precioLanzamiento = precioLanzamiento

                }

            } while (resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return consolaEncontrada
    }

    fun obtenerTodasLasConsolas(): ArrayList<BConsola> {
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = """
        SELECT * FROM CONSOLA
    """.trimIndent()
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(scriptConsultaLectura, null)

        val arregloConsolas = ArrayList<BConsola>()

        if (resultadoConsultaLectura.moveToFirst()) {
            do {
                val id = resultadoConsultaLectura.getInt(0)
                val nombre = resultadoConsultaLectura.getString(1)
                val fechaLanzamiento = resultadoConsultaLectura.getString(2)
                val descontinuado = resultadoConsultaLectura.getString(3)
                val cantidadMandos = resultadoConsultaLectura.getInt(4)
                val precioLanzamiento = resultadoConsultaLectura.getDouble(5)

                val consola = BConsola(id, nombre, fechaLanzamiento, descontinuado, cantidadMandos, precioLanzamiento)
                arregloConsolas.add(consola)
            } while (resultadoConsultaLectura.moveToNext())
        }

        resultadoConsultaLectura.close()
        baseDatosLectura.close()

        return arregloConsolas
    }


}