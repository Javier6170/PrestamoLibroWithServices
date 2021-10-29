package eam.edu.co.ingesoft.prestamoLibro.controllers

import eam.edu.co.ingesoft.prestamoLibro.model.entities.Book
import eam.edu.co.ingesoft.prestamoLibro.service.BookService
import eam.edu.co.ingesoft.prestamoLibro.service.BorrowService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

/**
 * crear Libro
 * editar libro
 */
@RestController
@RequestMapping("/books")
class BookController {
    @Autowired
    lateinit var bookService: BookService

    @Autowired
    lateinit var borrowService: BorrowService

    @PostMapping
    fun createLibro(@RequestBody book: Book) {
        bookService.createlibro(book)
    }

    @PutMapping("/{id}")
    fun editLibro(@PathVariable id: String, @RequestBody book: Book) {
        book.id = id
        bookService.editLibro(book)
    }

    @DeleteMapping("/{idUsuario}/{idBook}/{idBorrow}")
    fun entregarLibro(@PathVariable("idUsuario") idUsuario: String, @PathVariable("idBook") idBook: String, @PathVariable("idBorrow") idBorrow: Long) {
        bookService.entregarLibro(idUsuario, idBook,idBorrow)
    }

    @GetMapping("/{id}/borrows")
    fun getUsersHasBook(@PathVariable("id") idbook: String) = borrowService.findBybook(idbook)
}