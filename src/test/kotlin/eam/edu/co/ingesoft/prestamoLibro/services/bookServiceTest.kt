package eam.edu.co.ingesoft.prestamoLibro.services

import eam.edu.co.ingesoft.prestamoLibro.exception.BusinessException
import eam.edu.co.ingesoft.prestamoLibro.model.entities.Publisher
import eam.edu.co.ingesoft.prestamoLibro.model.entities.Book
import eam.edu.co.ingesoft.prestamoLibro.service.BookService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@SpringBootTest
@Transactional
class bookServiceTest {
    @Autowired
    lateinit var entityManager: EntityManager

    @Autowired
    lateinit var bookService: BookService

    @Test
    fun createUserHappyPathTest() {
        val publisher = Publisher(2, "Castellana")
        entityManager.persist(publisher)
        bookService.createlibro(Book("1", "178643438", 10, "Matematica Vectorial", publisher))

        val bookToAssert = entityManager.find(Book::class.java, "1")
        Assertions.assertNotNull(bookToAssert)
        Assertions.assertEquals("Matematica Vectorial", bookToAssert.nombre_Libro)
    }

    @Test
    fun createUserAlreadyExistsIdandNameTest() {
        //Prereqquisitos
        val publisher = Publisher(2, "Castellana")
        entityManager.persist(publisher)
        entityManager.persist(Book("1", "178643438", 10, "Matematica Vectorial", publisher))

        try {
            bookService.createlibro(Book("1", "178643438", 10, "Matematica Vectorial", publisher))
            Assertions.fail()
        } catch (e: BusinessException) {
            Assertions.assertEquals("This libro already exists", e.message)
        }
    }

    @Test
    fun entregarLibroTestHappyPath() {
        val publisher = Publisher(2, "Castellana")
        entityManager.persist(publisher)
        entityManager.persist(Book("1", "178643438", 10, "Matematica Vectorial", publisher))

        bookService.entregarLibro("1","1",1L)

        val bookAssert = entityManager.find(Book::class.java, "1")
        Assertions.assertEquals(11, bookAssert.cantidad)
    }

    @Test
    fun entregarLibroNotExists() {
        val publisher = Publisher(2, "Castellana")
        entityManager.persist(publisher)

        val exception = Assertions.assertThrows(
            BusinessException::class.java,
            { bookService.entregarLibro("1","1",1L) }
        )

        Assertions.assertEquals("This book does not exists", exception.message)
    }
}