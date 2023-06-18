import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
//import java.time.LocalDate
import java.util.*
import kotlinx.serialization.Serializable
import kotlinx.datetime.LocalDate


fun main(args: Array<String>) {


   /* val nintedo64 = Consola("Nintendo 64", LocalDate.of(1996, 5, 16), true, 4, 199.0)
    println("CONSOLA")
    println(nintedo64.nombre)
    println(nintedo64.fecha)
    println(nintedo64.descontinuado)
    println(nintedo64.cantidadMandos)
    println(nintedo64.precioLanzamiento)


    val videojuego = Videojuego("Super Mario 64", LocalDate.of(1996,6,15), "Nintendo", false, 59.99, nintedo64)
    println("VIDEOJUEGO")
    println(videojuego.nombre)
    println(videojuego.lanzamiento)
    println(videojuego.desarrollador)
    println(videojuego.multijugador)
    println(videojuego.precio)
    println(videojuego.consola.nombre)*/
    val consolas = mutableListOf<Consola>()

    print("¿Cuántas consolas deseas agregar? ")
    val cantidadConsolas = readLine()?.toIntOrNull() ?: 0

    for (i in 1..cantidadConsolas) {
        println("Consola $i:")
        val consola = ingresarDatosConsola()
        consolas.add(consola)
    }

    guardarDatosEnArchivo(consolas)


    /*val consola = ingresarDatosConsola()

    consolas.add(consola)
   /* for (i in 0 until consola.listaVideojuegos.size) {
        val videojuego = consola.listaVideojuegos[i]
        println("Videojuego ${i + 1}: ${videojuego.nombre}")
    }*/

    guardarDatosEnArchivo(consolas)*/
}

@Serializable
data class Consola(
    val nombre: String,
    val fecha: LocalDate,
    val descontinuado: Boolean,
    val cantidadMandos: Int,
    val precioLanzamiento: Double,
    val listaVideojuegos: MutableList<Videojuego> = mutableListOf() //Creando lista de videojuegos por consola
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

    print("¿Cuántos videojuegos deseas agregar a la consola? ")
    val cantidadVideojuegos = scanner.nextInt()

    for (i in 1..cantidadVideojuegos) {
        println("Ingresando datos del Videojuego $i:")
        val videojuego = ingresarDatosVideojuego()
        consola.listaVideojuegos.add(videojuego)
    }

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

fun imprimirDatosConsola(consola: Consola){
    println("CONSOLA" +
    "\nNombre consola: " + consola.nombre +
    "\nFecha de lanzamiento: " + consola.fecha +
    "\n¿Está descontinuado?: " + consola.descontinuado +
    "\nNúmero de mandos: " + consola.cantidadMandos +
    "\nPrecio de lanzamiento: " + consola.precioLanzamiento)
}

fun imprimirDatosVideojuego(videojuego: Videojuego){
    println("VIDEOJUEGO" +
            "\nNombre videojuego: " + videojuego.nombre +
            "\nFecha de lanzamiento: " + videojuego.lanzamiento +
            "\nDesarrollado por: " + videojuego.desarrollador +
            "\n¿Tiene multijugador?: " + videojuego.multijugador +
            "\nPrecio: " + videojuego.multijugador)
}

fun guardarDatosEnArchivo(consolas: List<Consola>){
    val json = Json.encodeToString(consolas)

    val file = File("data.json")
    file.writeText(json)

    println("Los datos se han guardado correctamente")


}