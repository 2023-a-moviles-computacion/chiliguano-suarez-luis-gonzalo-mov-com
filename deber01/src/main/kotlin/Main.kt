import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
//import java.time.LocalDate
import java.util.*
import kotlinx.serialization.Serializable
import kotlinx.datetime.LocalDate
import kotlinx.serialization.decodeFromString


fun main(args: Array<String>) {

    println("Bienvenido al programa de administración de consolas")

    var continuar = true
    val consolas = leerDatosDesdeArchivo()

    while (continuar) {
        println("Seleccione una opción:")
        println("1. Agregar una consola")
        println("2. Eliminar una consola")
        println("3. Editar una consola")
        println("4. Agregar un videojuego")
        println("5. Eliminar un videojuego")
        println("6. Editar un videojuego")
        println("7. Salir")

        val opcion = readLine()?.toIntOrNull()

        when (opcion) {

            1 -> {
                /*println("Consolas Disponibles:")
                mostrarConsolas(consolas)

                print("¿Cuántas consolas deseas agregar? ")
                val cantidadConsolas = readLine()?.toIntOrNull() ?: 0

                for (i in 1..cantidadConsolas) {
                    println("Consola $i:")
                    val consola = ingresarDatosConsola()
                    consolas.add(consola)
                }*/
                agregarConsolaAArchivo(consolas)

                guardarDatosEnArchivo(consolas)

            }
            2 -> {
                eliminarConsolaDesdeArchivo()
            }

            3 -> {
                editarConsolaDesdeArchivo()
            }

            4 -> {
                agregarVideojuegoPorIndice(consolas)
                guardarDatosEnArchivo(consolas)
            }

            5 -> {
                eliminarVideojuegoPorIndice(consolas)
                guardarDatosEnArchivo(consolas)

            }

            6 -> {
                editarVideojuegoPorIndice(consolas)
                guardarDatosEnArchivo(consolas)

            }

            7 -> {
                continuar = false
            }
            else -> {
                println("Opción inválida. Por favor, seleccione una opción válida.")
            }
        }
    }

    println("¡Hasta luego!")
}

@Serializable
data class Consola(
    val nombre: String,
    val fecha: LocalDate,
    val descontinuado: Boolean,
    val cantidadMandos: Int,
    val precioLanzamiento: Double,
    val listaVideojuegos: MutableList<Videojuego> = mutableListOf() //Una consola tendrá una lista de videojuegos
)
@Serializable
data class Videojuego(
    val nombre: String,
    val lanzamiento: LocalDate,
    val desarrollador: String,
    val multijugador: Boolean,
    val precio: Double
)

fun ingresarDatosConsola(): Consola {
    val scanner = Scanner(System.`in`)

    println("INGRESO DE DATOS PARA CONSOLA DE JUEGOS")

    println("Ingresa el nombre de la consola: ")
    val nombre = scanner.nextLine()

    println("Ingresa la fecha de lanzamiento de la consola (AAAA-MM-DD): ")
    val fechaString = scanner.nextLine();
    val fecha = LocalDate.parse(fechaString)

    println("¿La consola está descontiuada? (true o false): ")
    val descontinuado = scanner.nextBoolean()

    println("Ingrese la cantidad de mandos que acepta la consola: ")
    val cantidadMandos = scanner.nextInt()

    println("Ingrese el precio de lanzamiento de la consola: ")
    val precioLanzamiento = scanner.nextDouble()

    val consola = Consola(nombre, fecha, descontinuado, cantidadMandos, precioLanzamiento)

    return consola
}

fun ingresarDatosVideojuego(): Videojuego {
    val scanner = Scanner(System.`in`)

    println("INGRESO DE DATOS PARA VIDEOJUEGO")

    println("Ingresa el nombre del videojuego: ")
    val nombre = scanner.nextLine()

    println("Ingresa la fecha de lanzamiento del videojuego (AAAA-MM-DD): ")
    val lanzamientoString = scanner.nextLine();
    val lanzamiento = LocalDate.parse(lanzamientoString)

    println("Ingrese el nombre del desarrollador del videojuego: ")
    val desarrollador = scanner.nextLine()

    println("¿El juego  tiene multijugador? (true o false): ")
    val multijugador = scanner.nextBoolean()

    println("Ingrese el precio del juego: ")
    val precio = scanner.nextDouble()

    return Videojuego(nombre, lanzamiento, desarrollador, multijugador, precio)
}


fun leerDatosDesdeArchivo(): MutableList<Consola> {
    val file = File("data.json")

    return if (file.exists()) {
        val json = file.readText()
        Json.decodeFromString<MutableList<Consola>>(json)
    } else {
        mutableListOf()
    }
}

fun mostrarConsolas(consolas: List<Consola>) {
    for ((indice, consola) in consolas.withIndex()) {
        println("Índice: $indice " +
                "\n\tConsola: ${consola.nombre}" +
                "\n\tLanzamiento: ${consola.fecha}" +
                "\n\t¿Está descontinuado?: ${consola.descontinuado}" +
                "\n\tNúmero de mandos: ${consola.cantidadMandos}" +
                "\n\tPrecio de lanzamiento: ${consola.precioLanzamiento}")
    }
}


fun guardarDatosEnArchivo(consolas: List<Consola>){
    val json = Json.encodeToString(consolas)

    val file = File("data.json")
    file.writeText(json)

    println("Los datos se han guardado correctamente")

}

fun agregarConsolaAArchivo(consolas: MutableList<Consola>){

    println("Consolas Disponibles:")
    mostrarConsolas(consolas)

    print("¿Cuántas consolas deseas agregar? ")
    val cantidadConsolas = readLine()?.toIntOrNull() ?: 0

    for (i in 1..cantidadConsolas) {
        println("Consola $i:")
        val consola = ingresarDatosConsola()
        consolas.add(consola)
    }
}

fun eliminarConsolaDesdeArchivo() {
    val consolas = leerDatosDesdeArchivo()

    println("Consolas disponibles:")
    mostrarConsolas(consolas)

        print("Índice de la consola a eliminar: ")
        val indiceAEliminar = readLine()?.toIntOrNull() ?: -1

        if (indiceAEliminar >= 0 && indiceAEliminar < consolas.size) {
            consolas.removeAt(indiceAEliminar)
            guardarDatosEnArchivo(consolas)
            println("La consola ha sido eliminada correctamente.")
        } else {
            println("Índice inválido.")
        }
}

fun editarConsolaDesdeArchivo() {
    val consolas = leerDatosDesdeArchivo()

    println("Consolas disponibles:")
    mostrarConsolas(consolas)

        print("Índice de la consola a editar: ")
        val indiceAEditar = readLine()?.toIntOrNull() ?: -1

        if (indiceAEditar >= 0 && indiceAEditar < consolas.size) {
            val consolaEditada = ingresarDatosConsola()
            consolas[indiceAEditar] = consolaEditada
            guardarDatosEnArchivo(consolas)
            println("La consola ha sido editada correctamente.")
        } else {
            println("Índice inválido.")
        }

}

fun agregarVideojuegoPorIndice(consolas: MutableList<Consola>) {
    val scanner = Scanner(System.`in`)

    // Mostrar la lista de consolas
    println("Consolas disponibles:")
    mostrarConsolas(consolas)

    // Solicitar el índice de la consola
    print("Ingresa el índice de la consola a la cual quieres agregar un videojuego: ")
    val indiceConsola = scanner.nextInt()

    // Verificar si el índice es válido
    if (indiceConsola in 0 until consolas.size) {
        val consola = consolas[indiceConsola]

        // Solicitar el número de videojuegos que desea ingresar
        println("Ingresa el número de videojuegos que desea agregar")
        val numeroVideojuegos = scanner.nextInt()

        for(i in 1..numeroVideojuegos){
            println("Ingresando datos del Videojuego:")
            val videojuego = ingresarDatosVideojuego()

            // Agregar el videojuego a la consola
            consola.listaVideojuegos.add(videojuego)

            println("El videojuego $i se ha agregado correctamente a la consola ${consola.nombre}.")
        }

    } else {
        println("El índice de la consola no es válido.")
    }
}

fun eliminarVideojuegoPorIndice(consolas: List<Consola>) {
    val scanner = Scanner(System.`in`)

    // Mostrar la lista de consolas
    println("Consolas disponibles:")
    mostrarConsolas(consolas)

    print("Ingresa el índice de la consola de la cual quieres eliminar un videojuego: ")
    val indiceConsola = scanner.nextInt()

    // Verificar si el índice es válido
    if (indiceConsola in 0 until consolas.size) {
        val consola = consolas[indiceConsola]

        // Verificar si la consola tiene videojuegos
        if (consola.listaVideojuegos.isNotEmpty()) {
            println("Lista de videojuegos de la consola ${consola.nombre}:")
            for ((index, videojuego) in consola.listaVideojuegos.withIndex()) {
                println("\t$index. ${videojuego.nombre}")
            }

            print("Ingresa el índice del videojuego que deseas eliminar: ")
            val indiceVideojuego = scanner.nextInt()

            // Verificar si el índice del videojuego es válido
            if (indiceVideojuego in 0 until consola.listaVideojuegos.size) {
                val videojuego = consola.listaVideojuegos[indiceVideojuego]
                consola.listaVideojuegos.removeAt(indiceVideojuego)
                println("El videojuego ${videojuego.nombre} se ha eliminado correctamente de la consola ${consola.nombre}.")
            } else {
                println("El índice del videojuego no es válido.")
            }
        } else {
            println("La consola ${consola.nombre} no tiene videojuegos.")
        }
    } else {
        println("El índice de la consola no es válido.")
    }
}

fun editarVideojuegoPorIndice(consolas: List<Consola>) {
    val scanner = Scanner(System.`in`)

    // Mostrar la lista de consolas
    println("Consolas disponibles:")
    mostrarConsolas(consolas)

    print("Ingresa el índice de la consola de la cual quieres editar un videojuego: ")
    val indiceConsola = scanner.nextInt()

    // Verificar si el índice es válido
    if (indiceConsola in 0 until consolas.size) {
        val consola = consolas[indiceConsola]

        // Verificar si la consola tiene videojuegos
        if (consola.listaVideojuegos.isNotEmpty()) {
            println("Lista de videojuegos de la consola ${consola.nombre}:")
            for ((index, videojuego) in consola.listaVideojuegos.withIndex()) {
                println("\t$index. ${videojuego.nombre}")
            }

            print("Ingresa el índice del videojuego que deseas editar: ")
            val indiceVideojuego = scanner.nextInt()

            // Verificar si el índice del videojuego es válido
            if (indiceVideojuego in 0 until consola.listaVideojuegos.size) {
                val videojuego = consola.listaVideojuegos[indiceVideojuego]

                // Solicitar los nuevos datos del videojuego
                println("Ingresando nuevos datos para el videojuego ${videojuego.nombre}:")
                val nuevoVideojuego = ingresarDatosVideojuego()

                // Actualizar los datos del videojuego
                consola.listaVideojuegos[indiceVideojuego] = nuevoVideojuego

                println("El videojuego ${videojuego.nombre} se ha actualizado correctamente en la consola ${consola.nombre}.")
            } else {
                println("El índice del videojuego no es válido.")
            }
        } else {
            println("La consola ${consola.nombre} no tiene videojuegos.")
        }
    } else {
        println("El índice de la consola no es válido.")
    }
}



