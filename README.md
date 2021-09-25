# Seguimiento de tareas

![Portada](assets/portada.jpg)

## :point_up: Antes de empezar: algunos consejos

El enunciado tiene **mucha** información, van a necesitar leerlo varias veces. La sugerencia es que lo lean entero una vez (para tener una idea general) y luego vuelvan a consultarlo las veces que hagan falta.

Concentrensé en los requerimientos y, excepto que se traben mucho, respeten el orden sugerido. No es necesario que hagan TDD, pero sí sería interesante que vayan creando las distintas clases y métodos a medida que resuelven cada requerimiento y no antes.

En otras palabras: trabajen completando cada requerimiento antes de pasar al siguiente, con los tests que aseguran que funciona incluidos. Si al avanzar en los requerimientos les parece necesario refactorizar, adelante, van a tener los tests que garantizan que no rompieron nada. :smirk:

## :bookmark_tabs: Descripción del dominio

Una consultora de software necesita implementar un sistema para hacer el seguimiento del desarrollo de tareas. A estas tareas las clasificaremos en dos tipos distintos: **simples** y **de integración**.

### Tareas simples

Cada tarea simple tiene una cantidad de **horas estimadas** para ser terminada, algunos **trabajadores** asignados y un **responsable** - que no es más que un trabajador que asume ese rol particular para esa tarea (podría ocurrir perfectamente que sea trabajador en otra tarea). De cada trabajador se conoce **cuánto cobra por hora** trabajada. Las **horas necesarias** para finalizar una tarea son las horas estimadas que requiere divido la cantidad de trabajadores que tiene asignados (sin contar al responsable de la misma, que no aporta nada para reducir este número).

El costo de una tarea simple es el **costo de la infraestructura** necesaria para llevarla a cabo (que se configura para cada tarea) más los salarios que les corresponden a cada uno de los trabajadores asignados por cada hora que tuvieron que trabajar. Al responsable se le paga la totalidad de las horas estimadas de la tarea.

Esto mismo, en pseudocódigo, sería así:

```
Costo de Tarea Simple =
  Sumatoria de (Horas de trabajo de cada trabajador * Sueldo por hora de cada trabajador)
  + Horas estimadas * Sueldo del responsable
  + Costo de infraestructura

Horas de trabajo de cada trabajador = Horas estimadas / Cantidad de trabajadores
```

Por último, a una tarea simple podemos pedirle su **nómina de trabajadores**: una lista conformada por todos sus trabajadores, incluyendo al responsable.

### Tareas de integración

Este tipo de tareas consisten en coordinar otras tareas. Las tareas de integración no tienen un costo propio por infraestructura, ni trabajadores directamente a cargo, aunque sí tienen un responsable.

Las **horas necesarias** para realizarla se calculan como la suma de las horas necesarias para cumplir sus subtareas, más una hora para reuniones de planificación por cada 8 horas de trabajo. Se considera que su **costo** es la suma de los costos de sus subtareas más un bonus que se le paga al responsable, equivalente al 3% de esa suma. Para la **nómina de trabajadores**, se debe incluir a las nóminas de las subtareas más al responsable de la tarea de integración.

El sistema debe poder soportar que una tarea de integración tenga como subtarea tanto a tareas simples como a tareas de integración.

### Proyecto

Los proyectos son, ni más ni menos, una sucesión de tareas (simples o de integración) que deben ser realizadas para cumplir un objetivo. Para cada proyecto se configuran: las **tareas**, la **fecha de inicio** y una **fecha deseada de finalización**.

Por el momento hay un solo requerimiento asociado al proyecto: saber si está atrasado. Para esto, hay que calcular los **días estimados para finalizar** y sumárselos a la fecha de inicio. Si la fecha que resulta es, como máximo, el día siguiente a la fecha deseada, diremos que no está atrasado.

Los días estimados para finalizar se calculan de la siguiente forma: `sumatoria de horas necesarias de las tareas / 8 (que es lo que dura cada jornada laboral)`. Todos los datos necesarios salen de las tareas que el proyecto tenga.

:eyes: **Ojo:** para no complicar la cuenta, vamos a asumir que cada trabajador solo trabaja en una tarea a la vez.

## :heavy_check_mark: Requerimientos

### Etapa 1 - Modelo

Se pide resolver los siguientes requerimientos **sin** utilizar casteos ni chequeos de tipo (o sea, no vale usar `as` ni `is`).

1. Poder consultar la nómina de trabajadores de una tarea simple.
1. Saber cuántas horas se necesitan para finalizar una tarea simple.
1. Obtener el costo de una tarea simple.
1. Incorporar al modelo las tareas de integración.
1. Saber si un proyecto está atrasado.

### Etapa 2 - Conectando con el mundo real

El objetivo de esta etapa es poder tomar la información de los proyectos de un archivo, como una primera aproximación a lo que sería un sistema "real". Estos archivos están hechos en formato [JSON](https://www.json.org/json-es.html), y cada uno corresponde a un proyecto distinto. Hemos incluido algunos proyectos de ejemplo en la carpeta `data`, aunque bien podrían crearse otros - respetando el formato establecido.

Para que la información tenga sentido para un/a usuaria, agregaremos los siguientes atributos:

- `titulo` del proyecto,
- `descripcion` de las tareas simples,
- `nombre` del trabajador.

A modo de referencia, incluimos un ejemplo de un proyecto:

```json
{
  "titulo": "Batanar-Batanero-Incorrectamente",
  "inicio": "2022-08-09T14:19:40.366Z",
  "fin_deseado":"2022-10-02T05:51:08.801Z",
  "tareas": [
    {
      "simple": true,
      "trabajadores": [
        { "nombre": "Gary Centeno", "arancel": 3771 },
        { "nombre": "Madeline Holguín", "arancel": 2899 }
      ],
      "responsable": { "nombre": "Leona Gaytán", "arancel": 2054 },
      "horas": 9,
      "infra": 3485.81,
      "descripcion": "Fideo Gencianáceo General."
    },
    {
      "simple": false,
      "tareas": [ ... ],
      "responsable": { ... }
    }
  ]
}
```

Lo que se pide en esta etapa es:

1. Incorporar los atributos mencionados en los objetos de la etapa 1.
1. Poder generar los objetos de la etapa 1 a partir de la representación JSON de un proyecto.

:warning: **Importante:** para no tener que lidiar con la lectura de archivos, se incluye un objeto API que realiza esta tarea.

### Etapa 3 - Usando la aplicación

Llegó el momento de realmente conectar a nuestro programa con el mundo real, permitiendo que "cualquier persona" (que tenga una computadora, NodeJS y los conocimientos necesarios para ejecutarlo) pueda utilizarlo.

Para ello, vamos a programar una pequeña CLI, _command line interface_ o _interfaz por línea de comandos_, que nos permita visualizar los requerimientos de las dos primeras etapas, trayendo la información de los archivos JSON.

El diseño de la interfaz queda librado a su creatividad, siempre y cuando cumpla con los siguientes requerimientos:

1. Deben poder realizarse todas las consultas de las dos primeras etapas, interactuando con la API real.
1. En caso de que los datos que se ingresan sean erróneos, hay que mostrar algún mensaje amigable.
1. Incluir al menos un test por cada opción que tenga el CLI, y alguno donde se muestre un error. Simular la interacción de usuario y la interacción con la API utilizando impostores.

## :fountain_pen: Licencia

Esta obra fue elaborada por [Federico Aloi](https://github.com/faloi) y publicada bajo una [Licencia Creative Commons Atribución-CompartirIgual 4.0 Internacional][cc-by-sa].

[![CC BY-SA 4.0][cc-by-sa-image]][cc-by-sa]

[cc-by-sa]: https://creativecommons.org/licenses/by-sa/4.0/deed.es
[cc-by-sa-image]: https://licensebuttons.net/l/by-sa/4.0/88x31.png

### Créditos

:memo: [Enunciado original](https://sites.google.com/site/utndesign/material/guia-de-ejercicios/guia-objetos-patrones/tareas) creado por [Fernando Dodino](https://github.com/fdodino) y equipo de Diseño de Sistemas de Información (UTN - FRBA).

:camera_flash: Imagen de portada por <a href="https://unsplash.com/@brandsandpeople?utm_source=unsplash&utm_medium=referral&utm_content=creditCopyText">Brands&People</a> en <a href="https://unsplash.com/s/photos/papers?utm_source=unsplash&utm_medium=referral&utm_content=creditCopyText">Unsplash</a>.
