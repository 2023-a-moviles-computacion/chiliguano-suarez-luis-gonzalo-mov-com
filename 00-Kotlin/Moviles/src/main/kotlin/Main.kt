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

    //ARREGLOS
    //Tipos de Arreglos

    //Arreglo Estático
    val arregloEstatico: Array<Int> = arrayOf<Int>(1, 2, 3)
    println(arregloEstatico)

    //Arreglo Dinámico
    val arregloDinamico: ArrayList<Int> = arrayListOf<Int>(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    println(arregloDinamico)
    arregloDinamico.add(11)
    arregloDinamico.add(12)
    println(arregloDinamico)

    //OPERADORES -> Sirven para los arreglos estáticos y dinámicos

    //FOR EACH -> Unit
    //Iterar un arreglo
    val respuestaForEach: Unit = arregloDinamico
        .forEach { valorActual: Int ->
            println("Valor actual: ${valorActual}")
        }
    arregloDinamico.forEach{println(it)} //it (en ingles eso) significa el  elemento actual

    arregloEstatico
        .forEachIndexed { indice: Int, valorActual: Int ->
            println("Valor ${valorActual} Indice: ${indice}")
        }
    println(respuestaForEach)

    //MAP -> Muta el arreglo (Cambia el arreglo)
    //1) Enviemos el nuevo valor de la iteración
    //2) Nos devuelve es un NUEVO ARREGLO con los valores modificados

    val respuestaMap: List<Double> = arregloDinamico
        .map { valorActual: Int ->
            return@map valorActual.toDouble() + 100.00

        }

    println(respuestaMap)
    val respuestaMapDos = arregloDinamico.map { it + 15 }

    //Filter -> FILTRAR EL ARREGLO
    // 1) Devolver una expresion (TRUE o FALSE)
    // 2) Nuevo arreglo filtrado
    val respuestaFiltrer: List<Int> = arregloDinamico
        .filter { valorActual: Int ->
            val mayoresACinco: Boolean = valorActual > 5 //Expresion condicion
            return@filter mayoresACinco
        }
    val respuestaFilterDos = arregloDinamico.filter{ it <= 5}
    println(respuestaFiltrer)
    println(respuestaFilterDos)

    //OR AND
    //OR -> ANY (Alguno cumple?)
    //AND -> ALL (Todos cumplen?)

    val respuestaAny: Boolean = arregloDinamico
        .any{ valorActual: Int ->
            return@any (valorActual > 5)

        }
    println(respuestaAny) //true

    val respuestaAll: Boolean = arregloDinamico
        .all {valorActual: Int ->
            return@all (valorActual > 5)
        }
    println(respuestaAll) //false

    //REDUCE -> Valor acumulado
    // Valor acumulado = 0 (Siempre 0 en lenguaje Kotlin)
    // [1, 2, 3, 4, 5] -> Sumeme todos los valores del arreglo
    // valorIteracion1 = valorEmpieza + 1 = 0 + 1 = 1 -> Iteracion 1
    // valorIteracion2 = valorIteracion1 + 2 = 1 + 2 = 3 -> Iteracion 2
    // valorIteracion3 = valorIteracion2 + 3 = 3 + 3 = 6 -> Iteracion 3
    // valorIteracion4 = valorIteracion3 + 4 = 6 + 4 = 10 -> Iteracion 4
    // valorIteracion5 = valorIteracion4 + 5 = 10 + 5 = 15 -> Iteracion 5

    val respuestaReduce: Int = arregloDinamico
        .reduce{ //acumulado = 0 -> SIEMPRE EMPIEZA EN 0
                acumulado: Int, valorActual: Int ->
            return@reduce (acumulado + valorActual) // -> Logica de negocio

        }
    println(respuestaReduce) //78

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

