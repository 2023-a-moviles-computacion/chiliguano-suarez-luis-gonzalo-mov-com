package com.example.examenib

class BVideojuegos(
    var id: Int,
    var nombre: String?,
    var fechaLanzamiento: String?,
    var desarrollador: String?,
    var multijugadorOnline: String?,
    var precioLanzamiento: Double?
) {
    override fun toString(): String {
        return "${id} - ${nombre}"
    }

}
