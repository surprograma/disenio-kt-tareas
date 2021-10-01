package ar.edu.unahur.obj2.tareas

import TareasApi

// Acá encapsulamos el manejo de la consola real, desacoplandolo del programa en sí
object Consola {
  fun leerLinea() = readLine()
  fun escribirLinea(contenido: String) {
    println(contenido)
  }
}

// El código de nuestro programa, que (eventualmente) interactuará con una persona real
object Programa {
  // Lo ponemos en una variable para poder cambiarlo en el test
  var entradaSalida = Consola

  fun iniciar() {
    entradaSalida.escribirLinea("\uD83D\uDC4B ¡Hola mundo!")
    entradaSalida.escribirLinea("\uD83D\uDC4B ¿Cuál es tu nombre?")
    val nombre = entradaSalida.leerLinea()

    checkNotNull(nombre) { "Sin nombre no puedo hacer nada :(" }

    entradaSalida.escribirLinea("\uD83D\uDE03 Qué bueno verte por acá, ${nombre}.")
  }
}
