import java.time.LocalDate

fun main(args: Array<String>) {
    println("Hello World!")

    // Try adding program arguments via Run/Debug configuration.
    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
    println("Program arguments: ${args.joinToString()}")

    val nintedo64 = Consola("Nintendo 64", LocalDate.of(1996, 5, 16), true, 4, 199.0)
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
    println(videojuego.consola.nombre)
}

class Consola(
    val nombre: String,
    val fecha: LocalDate,
    val descontinuado: Boolean,
    val cantidadMandos: Int,
    val precioLanzamiento: Double
)

class Videojuego(
    val nombre: String,
    val lanzamiento: LocalDate,
    val desarrollador: String,
    val multijugador: Boolean,
    val precio: Double,
    val consola: Consola
)