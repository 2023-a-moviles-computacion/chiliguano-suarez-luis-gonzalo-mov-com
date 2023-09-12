package com.example.examen2b

class BVideojuego(
    var nombre: String,
    var fechaLanzamiento: String?,
    var desarrollador: String?,
    var multijugadorOnline: String?,
    var precioLanzamiento: Double?
)
{
    override fun toString(): String {
        return  "\nNombre: ${nombre}" +
                "\nLanzamiento: ${fechaLanzamiento}" +
                "\nDesarrolador: ${desarrollador}" +
                "\nMultijugador Online: ${multijugadorOnline}" +
                "\nPrecio de Lanzamiento: ${precioLanzamiento}"
    }
}

