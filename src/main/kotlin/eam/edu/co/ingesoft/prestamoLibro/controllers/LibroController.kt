package eam.edu.co.ingesoft.prestamoLibro.controllers

import eam.edu.co.ingesoft.prestamoLibro.model.Libro
import eam.edu.co.ingesoft.prestamoLibro.service.LibroService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

/**
* crear Libro
* editar libro
*/
@RestController
/**
 * todas las operaciones que se definan en este controlador empezaran por /person
 */
@RequestMapping("/libros")
class LibroController {
    @Autowired
    lateinit var libroService: LibroService

    @PostMapping
    fun createLibro(@RequestBody libro: Libro) {
        libroService.createlibro(libro)
    }

    @PutMapping("/libros/{id}") //el uri apunta a una persona especifica
    fun editLibro(@PathVariable id: String, @RequestBody libro: Libro) {
        libro.id = id
        libroService.editLibro(libro)
    }



}