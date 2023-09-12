package com.example.examen2b

class BConsola (
    var nombre:String,
    var fechaLanzamiento:String?,
    var descontinuado: String?,
    var cantidadMandos: Long?,
    var precioLanzamiento: Double?,
    var listaVideojuegos: List<BVideojuego>?
    ) {

        override fun toString(): String {
            return  "\nNombre: ${nombre}" +
                    "\nLanzamiento: ${fechaLanzamiento}" +
                    "\nDescontinuado: ${descontinuado}" +
                    "\nCantidad de Mandos: ${cantidadMandos}" +
                    "\nPrecio de Lanzamiento: ${precioLanzamiento}"
        }

    }
