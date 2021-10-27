package eam.edu.co.ingesoft.prestamoLibro.repositories

import eam.edu.co.ingesoft.prestamoLibro.model.entities.Author
import eam.edu.co.ingesoft.prestamoLibro.model.entities.Publisher
import eam.edu.co.ingesoft.prestamoLibro.model.entities.Book
import eam.edu.co.ingesoft.prestamoLibro.model.entities.AuthorBook
import eam.edu.co.ingesoft.prestamoLibro.repository.AuthorBookRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@SpringBootTest
@Transactional
class bookAuthorTest {
    @Autowired
    lateinit var authorBookRepository: AuthorBookRepository

    @Autowired
    lateinit var entityManager: EntityManager

    @Test
    fun contextLoads() {
    }

    @Test
    fun testCreate() {
        authorBookRepository.create(
            AuthorBook(1L, Author(2, "juan", "marin"),
            Book("1","178643438",10,"Matematica Vectorial", Publisher(2,"Castellana"))
        )
        )

        val libroAutor = entityManager.find(AuthorBook::class.java,1L)
        Assertions.assertNotNull(libroAutor)
        Assertions.assertEquals(1L, libroAutor.id)
        Assertions.assertEquals("Matematica Vectorial", libroAutor.book.nombre_Libro)
        Assertions.assertEquals("Castellana", libroAutor.book.id_publisher.nombre_editorial)
    }

    @Test
    fun testUpdate() {
        //prerequisito
        entityManager.persist(
            AuthorBook(1L, Author(2, "juan", "marin"),
                Book("1","178643438",10,"Matematica Vectorial", Publisher(2,"Castellana"))
            )
        )

        //ejecutando...
        val libroAutor = entityManager.find(AuthorBook::class.java, 1L)
        libroAutor.book.isbn_libro = "1020-4040-89871"
        libroAutor.book.nombre_Libro = "Castellano basico"
        libroAutor.book.id_publisher.nombre_editorial = "Babel"
        libroAutor.author.name = "javier"

        authorBookRepository.update(libroAutor)

        //assersiones
        val libroAutorAssert = entityManager.find(AuthorBook::class.java, 1L)
        Assertions.assertEquals("1020-4040-89871", libroAutorAssert.book.isbn_libro)
        Assertions.assertEquals("Castellano basico", libroAutorAssert.book.nombre_Libro)
        Assertions.assertEquals("Babel", libroAutorAssert.book.id_publisher.nombre_editorial)
        Assertions.assertEquals("javier", libroAutorAssert.author.name)
    }

    @Test
    fun findTest() {
        entityManager.persist(
            AuthorBook(1L, Author(2, "juan", "marin"),
                Book("1","178643438",10,"Matematica Vectorial", Publisher(2,"Castellana"))
            )
        )

        val libroAutor = authorBookRepository.find(1L)

        Assertions.assertNotNull(libroAutor)
        Assertions.assertEquals("Matematica Vectorial", libroAutor?.book?.nombre_Libro)
        Assertions.assertEquals("Castellana", libroAutor?.book?.id_publisher?.nombre_editorial)
        Assertions.assertEquals("178643438", libroAutor?.book?.isbn_libro)
        Assertions.assertEquals("juan", libroAutor?.author?.name)
    }


    @Test
    fun testDelete() {
        entityManager.persist(
            AuthorBook(1L, Author(2, "juan", "marin"),
                Book("1","178643438",10,"Matematica Vectorial", Publisher(2,"Castellana"))
            )
        )

        //ejecucion de la preuba
        authorBookRepository.delete(1L)

        //assersiones
        val libroAutor = entityManager.find(AuthorBook::class.java, 1L)
        Assertions.assertNull(libroAutor)
    }

    @Test
    fun findByAutor(){
        val author = Author(2L, "juan", "marin")
        entityManager.persist(author)
        val publisher = Publisher(2L,"Castellana")
        entityManager.persist(publisher)
        val book = Book("1","178643438",10,"Matematica Vectorial",publisher)
        entityManager.persist(book)
        val book2 = Book("2","6562648465",10,"ingles avanzado",publisher)
        entityManager.persist(book2)
        val book3 = Book("3","6465165498",10,"Introduccion a los lenguajes de programacion",publisher)
        entityManager.persist(book3)

        entityManager.persist(AuthorBook(1L, author,book))
        entityManager.persist(AuthorBook(2L, author,book2))
        entityManager.persist(AuthorBook(3L, author,book3))

        //ejecutando pruebas
        val autores = authorBookRepository.findByAutor(2L)

        //assertions
        Assertions.assertEquals(3,autores.size)
    }

    @Test
    fun findByLibro(){
        val author = Author(2L, "juan", "marin")
        entityManager.persist(author)
        val author2 = Author(3L, "santiago", "gutierrez")
        entityManager.persist(author2)
        val author3 = Author(4L, "felipe", "garcia")
        entityManager.persist(author3)
        val publisher = Publisher(2L,"Castellana")
        entityManager.persist(publisher)
        val book = Book("1","178643438",10,"Matematica Vectorial",publisher)
        entityManager.persist(book)
        entityManager.persist(AuthorBook(1L, author,book))
        entityManager.persist(AuthorBook(2L, author2,book))
        entityManager.persist(AuthorBook(3L, author3,book))

        //ejecutando pruebas
        val libros = authorBookRepository.findByLibro("1")

        //assertions
        Assertions.assertEquals(3,libros.size)
    }
}