package eam.edu.co.ingesoft.prestamoLibro.service

import eam.edu.co.ingesoft.prestamoLibro.exception.BusinessException
import eam.edu.co.ingesoft.prestamoLibro.model.entities.Book
import eam.edu.co.ingesoft.prestamoLibro.repository.BookRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.persistence.EntityManager
import javax.persistence.EntityNotFoundException

@Service
class BookService {
    @Autowired
    lateinit var bookRepository: BookRepository

    @Autowired
    lateinit var entityManager: EntityManager

    fun createlibro(book: Book) {
        val librorById = bookRepository.find(book.id)
        val libroNames = bookRepository.listNamesBooks(book)

        if (librorById != null && libroNames != null) {
            throw BusinessException("This libro already exists")
        }
        bookRepository.create(book)
    }

    fun editLibro(book: Book){
        bookRepository.find(book.id?:"") ?: throw EntityNotFoundException("This book does not exist")
        bookRepository.update(book)
    }

    fun entregarLibro(id_Libro: String, id_usuario: String) {
        bookRepository.find(id_Libro)
            ?: throw BusinessException("This book does not exists")
        val bookFind = entityManager.find(Book::class.java, id_Libro)
        val cantidadAnterior = bookFind.cantidad
        val cantidadNueva = cantidadAnterior + 1
        bookFind.cantidad = cantidadNueva

        bookRepository.update(bookFind)
    }
}