package com.example.examen2b

class BVideojuego(
    var id: Int,
    var nombre: String?,
    var fechaLanzamiento: String?,
    var desarrollador: String?,
    var multijugadorOnline: String?,
    var precioLanzamiento: Double?,
    var consolaID: Int
)
{
    override fun toString(): String {
        return "ID: ${id}" +
                "\nNombre: ${nombre}" +
                "\nLanzamiento: ${fechaLanzamiento}" +
                "\nDesarrolador: ${desarrollador}" +
                "\nMultijugador Online: ${multijugadorOnline}" +
                "\nPrecio de Lanzamiento: ${precioLanzamiento}" +
                "\nConsola ID: ${consolaID}"
    }
}

