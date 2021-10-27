package eam.edu.co.ingesoft.prestamoLibro.repositories

import eam.edu.co.ingesoft.prestamoLibro.model.entities.Publisher
import eam.edu.co.ingesoft.prestamoLibro.model.entities.Book
import eam.edu.co.ingesoft.prestamoLibro.repository.BookRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@SpringBootTest
@Transactional
class bookRepositoryTest {
    @Autowired
    lateinit var bookRepository: BookRepository

    @Autowired
    lateinit var entityManager: EntityManager

    @Test
    fun contextLoads() {
    }

    @Test
    fun testCreate() {
        bookRepository.create(Book("1","178643438",10,"Matematica Vectorial", Publisher(2,"Castellana")))

        val book = entityManager.find(Book::class.java,"1")
        Assertions.assertNotNull(book)
        Assertions.assertEquals("178643438", book.isbn_libro)
        Assertions.assertEquals("Matematica Vectorial", book.nombre_Libro)
        Assertions.assertEquals("Castellana", book.id_publisher.nombre_editorial)
    }

    @Test
    fun testUpdate() {
        //prerequisito
        entityManager.persist(Book("1","178643438",10,"Matematica Vectorial", Publisher(2,"Castellana")))

        //ejecutando...
        val book = entityManager.find(Book::class.java, "1")
        book.isbn_libro = "1020-4040-89871"
        book.nombre_Libro = "Castellano basico"
        book.id_publisher.nombre_editorial = "Babel"

        bookRepository.update(book)

        //assersiones
        val bookAssert = entityManager.find(book::class.java, "1")
        Assertions.assertEquals("1020-4040-89871", bookAssert.isbn_libro)
        Assertions.assertEquals("Castellano basico", bookAssert.nombre_Libro)
        Assertions.assertEquals("Babel", bookAssert.id_publisher.nombre_editorial)
    }

    @Test
    fun findTest() {
        entityManager.persist(Book("1","178643438",10,"Matematica Vectorial", Publisher(2,"Castellana")))

        val libro = bookRepository.find("1")

        Assertions.assertNotNull(libro)
        Assertions.assertEquals("Matematica Vectorial", libro?.nombre_Libro)
        Assertions.assertEquals("Castellana", libro?.id_publisher?.nombre_editorial)
        Assertions.assertEquals("178643438", libro?.isbn_libro)
    }


    @Test
    fun testDelete() {
        entityManager.persist(Book("1","178643438",10,"Matematica Vectorial", Publisher(2,"Castellana")))

        //ejecucion de la preuba
        bookRepository.delete("1")

        //assersiones
        val book = entityManager.find(Book::class.java, "1")
        Assertions.assertNull(book)
    }

    @Test
    fun testFindByEditorial(){
        val publisher = Publisher(1L,"Castellana")
        entityManager.persist(publisher)
        entityManager.persist(Book("1","654646464",10,"Matematica vectorial",publisher))
        entityManager.persist(Book("2","9151651165",10,"Castellano basico",publisher))


        //ejecutando pruebas
        val editoriales = bookRepository.findByEditorial(1L)

        //assertions
        Assertions.assertEquals(2,editoriales.size)
    }
}