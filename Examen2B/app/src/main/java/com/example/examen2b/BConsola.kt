package com.example.examenib

class BConsola(
    var id: Int,
    var nombre:String?,
    var fechaLanzamiento:String?,
    var descontinuado: String?,
    var cantidadMandos: Int?,
    var precioLanzamiento: Double?,
) {

    override fun toString(): String {
        return "ID: ${id}" +
                "\nNombre: ${nombre}" +
                "\nLanzamiento: ${fechaLanzamiento}" +
                "\nDescontinuado: ${descontinuado}" +
                "\nCantidad de Mandos: ${cantidadMandos}" +
                "\nPrecio de Lanzamiento: ${precioLanzamiento}"
    }

}