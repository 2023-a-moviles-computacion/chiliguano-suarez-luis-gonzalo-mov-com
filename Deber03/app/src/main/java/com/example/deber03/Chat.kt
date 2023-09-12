package com.example.deber03

class Chat (
    var nombreUsuario: String,
    var mensaje: String,
    var fecha: String,
    var nombreImagenPerfil: String,
){

    companion object {
        val arregloChats = arrayListOf<Chat>()
        init {
            arregloChats
                .add(
                    Chat("Isaí", "No lo creo  ", "-  1:13 a.m.", "chat1")
                )
            arregloChats
                .add(
                    Chat("Anthony Chamba", "Es verdad, lo soy :(  ", "-  sáb", "chat2")
                )
            arregloChats
                .add(
                    Chat("Kevin xd", "Era para mañana  ", "-  20 ago", "chat3")
                )
            arregloChats
                .add(
                    Chat("Rommel M", "Si  ", "-  3 ago", "chat4")
                )
            arregloChats
                .add(
                    Chat("Roberto Carlos", "El gato que etá...  ", "-  22 jul", "chat5")
                )
            arregloChats
                .add(
                    Chat("BJA", "You know  ", "-  2 may", "chat6")
                )
            arregloChats
                .add(
                    Chat("Fedelobo", "Y YAA!!   ", "-  21 may", "chat7")
                )
            arregloChats
                .add(
                    Chat("Alisson", "Mañana respondo  ", "-  14 abr", "chat8")
                )
            arregloChats
                .add(
                    Chat("Ozzy", "It's over forever  ", "-  13 abr", "chat9")
                )

        }
    }

}