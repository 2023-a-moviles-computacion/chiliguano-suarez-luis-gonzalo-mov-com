package com.example.examen2b

class BConsola (
    var id: String?,
    var nombre:String?,
    var fechaLanzamiento:String?,
    var descontinuado: String?,
    var cantidadMandos: Long?,
    var precioLanzamiento: Double?
    ) {

        override fun toString(): String {
            return  "ID:  ${id}"+
                    "\nNombre: ${nombre}" +
                    "\nLanzamiento: ${fechaLanzamiento}" +
                    "\nDescontinuado: ${descontinuado}" +
                    "\nCantidad de Mandos: ${cantidadMandos}" +
                    "\nPrecio de Lanzamiento: ${precioLanzamiento}"
        }

    }
