package com.example.examen2b

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ESqliteHelper(
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

    //MÉTODOS PARA LOS VIDEOJUEGOS

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
        multijugadorOnline: String,
        precioLanzamiento: Double,
        id: Int,
    ): Boolean{
        val conexionEscritura = writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("nombre", nombre)
        valoresAActualizar.put("fechaLanzamiento", fechaLanzamiento)
        valoresAActualizar.put("desarrollador", desarrollador)
        valoresAActualizar.put("multijugadorOnline", multijugadorOnline)
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

    fun consultarVideojuegoPorId(id:Int):BVideojuego{
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
        val vieojuegoEncontrado = BVideojuego(0, "", "", "", "", 0.0, 0)
        val arreglo = arrayListOf<BVideojuego>()
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

    fun obtenerVideojuegosDeConsola(consolaId: Int): ArrayList<BVideojuego> {
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura =
            """
        SELECT * FROM VIDEOJUEGO WHERE consolaID = ?
    """.trimIndent()
        val parametrosConsultaLectura = arrayOf(consolaId.toString())
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(scriptConsultaLectura, parametrosConsultaLectura)

        val arregloVideojuegos = ArrayList<BVideojuego>()

        if (resultadoConsultaLectura.moveToFirst()) {
            do {
                val id = resultadoConsultaLectura.getInt(0)
                val nombre = resultadoConsultaLectura.getString(1)
                val lanzamiento = resultadoConsultaLectura.getString(2)
                val desarrollador = resultadoConsultaLectura.getString(3)
                val multijugadorOnline = resultadoConsultaLectura.getString(4)
                val precioLanzamiento = resultadoConsultaLectura.getDouble(5)
                val consolaID = resultadoConsultaLectura.getInt(6)

                val videojuego = BVideojuego(id, nombre, lanzamiento, desarrollador, multijugadorOnline, precioLanzamiento, consolaID)
                arregloVideojuegos.add(videojuego)
            } while (resultadoConsultaLectura.moveToNext())
        }

        resultadoConsultaLectura.close()
        baseDatosLectura.close()

        return arregloVideojuegos
    }




}