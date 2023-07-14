package com.example.examenib

class BBaseDatosMemoria {

    companion object{
        val arregloBConsolas = arrayListOf<BConsola>()
        init{
            arregloBConsolas
                .add(

                    BConsola(1, "NES", "12-12-1989", "SI", 2, 199.99)
                )
            arregloBConsolas
                .add(

                    BConsola(2, "MEGADRIVE", "11-12-1990", "SI", 2, 159.99)
                )

        }
    }
}