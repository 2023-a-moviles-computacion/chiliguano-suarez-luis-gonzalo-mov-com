package com.example.examenib

class BVideojuego(
    var id: Int,
    var nombre: String?,
    var fechaLanzamiento: String?,
    var desarrollador: String?,
    var multijugadorOnline: String?,
    var precioLanzamiento: Double?,
    var consolaID: Int
) {
    override fun toString(): String {
        return "ID: ${id}" +
                "Nombre: ${nombre}" +
                "Lanzamiento: ${fechaLanzamiento}" +
                "Desarrolador: ${desarrollador}" +
                "Multijugador Online: ${multijugadorOnline}" +
                "Precio de Lanzamiento: ${precioLanzamiento}" +
                "Consola ID: ${consolaID}"
    }

}
