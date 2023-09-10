package com.example.deber03

class Historia (
    var nombreUsuario: String,
    var nombreImagenPerfil: String,
    var nombreImagenHistoria: String,
) {
    companion object {
        val arregloHistorias = arrayListOf<Historia>()
        init {
            arregloHistorias
                .add(
                    Historia("Isaí",  "chat1", "historia1")
                )
            arregloHistorias
                .add(
                    Historia("Anthony Chamba",  "chat2", "historia2")
                )
            arregloHistorias
                .add(
                    Historia("Kevin xd",  "chat3", "historia3")
                )
            arregloHistorias
                .add(
                    Historia("Rommel M",  "chat4", "historia4")
                )
            arregloHistorias
                .add(
                    Historia("Roberto Carlos",  "chat5", "historia5")
                )
            arregloHistorias
                .add(
                    Historia("BJA",  "chat6", "historia6")
                )
        }
    }

}