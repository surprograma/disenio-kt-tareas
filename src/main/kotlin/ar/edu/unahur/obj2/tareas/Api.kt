import com.beust.klaxon.Klaxon

data class ProyectoJson(
  val titulo: String,
  val inicio: String,
  val fin_deseado: String,
  val tareas: List<TareaJson>
)

open class TareaJson(val simple: Boolean)

data class TareaSimpleJson(
  val trabajadores: List<TrabajadorJson>,
  val responsable: TrabajadorJson,
  val horas: Int,
  val infra: Int,
  val descripcion: String
) : TareaJson(true)

data class TareaIntegracionJson(
  val tareas: List<TareaJson>,
  val responsable: TrabajadorJson
) : TareaJson(false)

data class TrabajadorJson(
  val nombre: String,
  val arancel: Int
)

object Api {
  fun importarProyecto(nombre: String): ProyectoJson {
    val json = javaClass.getResourceAsStream ("${nombre}.json")
      ?: error("No se encontró un archivo para `${nombre}`. ¿Habrás escrito mal el nombre?")
    return Klaxon().parse<ProyectoJson>(json)!!
  }
}
