import java.util.*

fun main(args: Array<String>) {
    println("Hello World!")

    // Try adding program arguments via Run/Debug configuration.
    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
    println("Program arguments: ${args.joinToString()}")

    //Tipos de Variables
    //Inmutables (No se reasignan "=")
    val inmutable: String = "Adrian";
    //inmutable = "Vicente";

    //Mutables (Re asignar)
    var mutable: String = "Vicente";
    mutable = "Adrian";

    //Se prefiere val antes que var

    //Duck Typing
    val ejemploVariable = "Adrian Eguez"
    val edadEjemplo: Int = 17
    ejemploVariable.trim()
    //ejemploVariable = edadEjemplo;

    //Variable primitiva
    val nombreProfesor: String = "Adrian Eguez"
    val sueldo: Double = 1.2
    val estadoCivil: Char = 'C'
    val mayorEdad: Boolean = true
    //Clase Java
    val fechaNacimiento: Date = Date()

    //Switch
    val estadoCivilWhen = "C"
    when (estadoCivilWhen){
        ("C") -> {
            println("Casado")
        }
        ("S") -> {
            println("Soltero")
        }
        else -> {
            println("No sabemos")
        }
    }

    val coqueteo = if (estadoCivilWhen == "S") "Si" else "No"

    calcularSueldo(10.00)
    calcularSueldo(10.00, 12.00, 20.00)
    calcularSueldo(10.00, bonoEspecial = 20.00) //Parametros nombrados
    calcularSueldo(bonoEspecial = 20.00, sueldo = 10.00, tasa = 14.00)

    val sumaUno = Suma(1,1)
    val sumaDos = Suma(null, 1)
    val sumaTres = Suma(1, null)
    val sumaCuatro = Suma(null, null)
    sumaUno.sumar()
    sumaDos.sumar()
    sumaTres.sumar()
    sumaCuatro.sumar()
    println(Suma.pi)
    println(Suma.elevarAlCuadrado(2))
    println(Suma.historialSumas)
}

//void -> Unit
fun imprimirNombre(nombre: String): Unit{
    println("Nombre : ${nombre}")
}


fun calcularSueldo(
    //Int -> Int? (nullable)
    //String -> String? (nullable)
    //Date -> String? (nullable)

    sueldo: Double, //Requerido
    tasa: Double = 12.00, // Opcional (por defecto)
    bonoEspecial: Double? = null, //Opcion null -> nullable
): Double{
    if(bonoEspecial == null){
        return sueldo * (100/tasa)
    } else {
        return sueldo * (100/tasa) + bonoEspecial
    }

}

abstract class NumerosJava{
    protected val numeroUno: Int
    private val numeroDos: Int

    constructor(
        uno: Int,
        dos: Int
    ){//Bloque de codigo del constructor
        this.numeroUno = uno
        this.numeroDos = dos
        println("Inicializando")

    }
}

abstract class Numeros( //Constructor Primairo
// Ejemplo:
// uno: Int, (Parametro (sin modificador de acceso))
// private var uno: Int, //Propiedad de la clase (por defecto es Public)
// public var uno: Int,
protected val numeroUno: Int, //Propiedad de la clase protected numeros.numeroUno
protected val numeroDos: Int, //Propiedad de la clase protected numeros.numeroDos
 ){
    //var cedula: string = "" (public)
    //private valorCalculado: Int = 0 (private)

    init{
        this.numeroUno; this.numeroDos; //this es opcional
        numeroUno; numeroDos;
        println("Inicializando")
    }


}

class Suma( //Constructor Primario Suma
    uno: Int, //Parametro
    dos: Int //Parametro
) : Numeros(uno, dos) { // <- Constructor del Padre
    init { //Bloque constructor primario
        this.numeroUno; numeroUno;
        this.numeroDos; numeroDos;
    }

    constructor( //Segundo constructor
        uno: Int?, //parametros
        dos: Int //parametros
    ) : this( //llamada constructor primario
        if (uno == null) 0 else uno,
        dos
    ) { //si necesitamos bloque de código lo usamos
        numeroUno;
    }

    constructor( //tercer constructor
        uno: Int, //parametros
        dos: Int? //parametros
    ) : this( //llamada constructor primario
        uno,
        if (dos == null) 0 else uno
    ) //Si no necesitamos el bloque de código "{}" lo omitimos

    constructor( //Cuarto constructor
        uno: Int?, //parametros
        dos: Int? //parametros
    ) : this( //llamada al consctructor primario
        if (uno == null) 0 else uno,
        if(dos == null) 0 else dos
    )

    public fun sumar(): Int { // public por defecto, o usar private o protected
        val total = numeroUno + numeroDos

        agregarHistorial(total)
        return total
    }

    companion object{ //Atributos y Metodos "Comaprtidos"
        // entre las instancias
        val pi = 3.14
        fun elevarAlCuadrado(num: Int): Int{
            return num * num
        }
        val historialSumas = arrayListOf<Int>()
            fun agregarHistorial(valorNuevaSuma: Int){
                historialSumas.add(valorNuevaSuma)
            }
    }
}

