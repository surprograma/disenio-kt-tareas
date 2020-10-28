# Seguimiento de tareas

![Tareas](assets/tareas.jpg)

## Antes de empezar: algunos consejos

El enunciado tiene **mucha** información, van a necesitar leerlo varias veces. La sugerencia es que lo lean entero una vez (para tener una idea general) y luego vuelvan a consultarlo las veces que hagan falta.

Concentrensé en los requerimientos y, excepto que se traben mucho, respeten el orden sugerido. No es necesario que hagan TDD, pero sí sería interesante que vayan creando las distintas clases y métodos a medida que resuelven cada requerimiento y no antes. 

En otras palabras: trabajen completando cada requerimiento antes de pasar al siguiente, con los tests que aseguran que funciona incluidos. Si al avanzar en los requerimientos les parece necesario refactorizar, adelante, van a tener los tests que garantizan que no rompieron nada. :smirk: 

## Descripción del dominio

Una consultora de software necesita implementar un sistema para hacer el seguimiento del desarrollo de tareas.

Cada tarea tiene una cantidad de **horas estimadas** para ser terminada, un **responsable** y algunos **empleados** asignados. De cada empleado se conoce **cuánto cobra por hora** trabajada. Las **horas necesarias** para finalizar una tarea son las horas estimadas que requiere divido la cantidad de empleados que tiene asignados (sin contar al responsable de la misma, que no aporta nada para reducir este número).

El costo de una tarea es el **costo de la infraestructura** necesaria para llevarla a cabo (que se configura para cada tarea) más los salarios que les corresponden a cada uno de los empleados asignados por cada hora que tuvieron que trabajar. Al responsable se le paga la totalidad de las horas estimadas de la tarea.

Esto mismo, en pseudocódigo, sería así:

```
Costo de Tarea = 
  (Horas Estimadas / Nro. de Empleados) * Sueldo por hora de Cada Empleado + 
  Horas Estimadas * Sueldo del Responsable + 
  costo de infraestructura
```

## Requerimientos

Se pide resolver los siguientes requerimientos **sin** utilizar casteos ni chequeos de tipo (o sea, no vale usar `as` ni `is`). 

1. Poder consultar la nómina de empleados de una tarea (una lista), conformada por sus empleados y su responsable. 
1. Saber cuántas horas se necesitan para finalizar una tarea.
1. Obtener el costo de una tarea.
1. Incorporar al modelo las "tareas de integración", que consisten en coordinar otras tareas. Tener en cuenta lo siguiente:
   * Estas tareas no tienen un costo propio por infraestructura, ni empleados directamente a cargo, aunque sí tienen un responsable.
   * Las horas necesarias para realizarla se calculan como la suma de lo que tardan sus subtareas, más una hora para reuniones de planificación por cada 8 horas de trabajo real.
   * Se considera que su costo es la suma de los costos de sus subtareas más un bonus que se le paga al responsable, equivalente al 3% de esa suma.
   * Para la nómina, se debe incluir a las nóminas de las subtareas más al responsable de la tarea de integración.

## Créditos

[Enunciado original](https://sites.google.com/site/utndesign/material/guia-de-ejercicios/guia-objetos-patrones/tareas) creado por Fernando Dodino y el equipo de Diseño de Sistemas de Información para UTN - FRBA. Transformado a Markdown, reformateado y reorganizado por Federico Aloi para UNaHur.
