import com.beust.klaxon.Klaxon
import com.beust.klaxon.TypeAdapter
import com.beust.klaxon.TypeFor
import kotlin.reflect.KClass

data class ProyectoJson(
  val titulo: String,
  val inicio: String,
  val fin_deseado: String,
  val tareas: List<TareaJson>
)

@TypeFor(field = "simple", adapter = TareaJsonAdapter::class)
open class TareaJson(
  val simple: Boolean
)

class TareaJsonAdapter: TypeAdapter<TareaJson> {
  override fun classFor(type: Any): KClass<out TareaJson> =
    if (type as Boolean) { TareaSimpleJson::class } else { TareaIntegracionJson::class }
}

data class TareaSimpleJson(
  val trabajadores: List<TrabajadorJson>?,
  val responsable: TrabajadorJson,
  val horas: Int?,
  val infra: Double?,
  val descripcion: String
) : TareaJson(true)

data class TareaIntegracionJson(
  val tareas: List<TareaJson>?,
  val responsable: TrabajadorJson
) : TareaJson(false)

data class TrabajadorJson(
  val nombre: String,
  val arancel: Int
)

object TareasApi {
  fun importarProyecto(nombre: String): ProyectoJson {
    val json = javaClass.getResourceAsStream ("${nombre}.json")
      ?: error("No se encontró un archivo para `${nombre}`. ¿Habrás escrito mal el nombre?")
    return Klaxon().parse<ProyectoJson>(json)!!
  }
}
