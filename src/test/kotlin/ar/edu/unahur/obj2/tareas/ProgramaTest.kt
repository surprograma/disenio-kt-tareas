package ar.edu.unahur.obj2.tareas

import io.kotest.core.spec.style.DescribeSpec
import io.mockk.*


class ProgramaTest : DescribeSpec({
  describe("Programa") {
    val consolaMock = mockk<Consola>()

    // Configuramos un mock para la entrada salida
    // TODO: hacer lo mismo para la API
    Programa.entradaSalida = consolaMock

    // Indicamos que los llamados a `escribirLinea` no hacen nada (just Runs)
    every { consolaMock.escribirLinea(any()) } just Runs

    it("saluda a quien lo ejecuta") {
      // Cuando se llame a `leerLinea()`, simulamos que la usuaria escribió "Mailén".
      // Notar que esto lo configuramos *antes* de iniciar el programa,
      // para que cuando efectivamente se llame al método ya el mock sepa qué tiene que hacer.
      every { consolaMock.leerLinea() } returns "Mailén"

      // Iniciamos el programa
      Programa.iniciar()

      // Verificamos que se escribió "por pantalla" el resultado esperado
      verify {
        consolaMock.escribirLinea("\uD83D\uDE03 Qué bueno verte por acá, Mailén.")
      }
    }
  }
})
