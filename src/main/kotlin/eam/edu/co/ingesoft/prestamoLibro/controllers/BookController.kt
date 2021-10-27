package eam.edu.co.ingesoft.prestamoLibro.controllers

import eam.edu.co.ingesoft.prestamoLibro.model.entities.Book
import eam.edu.co.ingesoft.prestamoLibro.service.BookService
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
@RequestMapping("/books")
class BookController {
    @Autowired
    lateinit var bookService: BookService

    @PostMapping
    fun createLibro(@RequestBody book: Book) {
        bookService.createlibro(book)
    }


    @PutMapping("/{id}") //el uri apunta a una persona especifica
    fun editLibro(@PathVariable id: String, @RequestBody book: Book) {
        book.id = id
        bookService.editLibro(book)
    }

}