package eam.edu.co.ingesoft.prestamoLibro.services

import eam.edu.co.ingesoft.prestamoLibro.exception.BusinessException
import eam.edu.co.ingesoft.prestamoLibro.model.entities.Publisher
import eam.edu.co.ingesoft.prestamoLibro.model.entities.Book
import eam.edu.co.ingesoft.prestamoLibro.model.entities.Borrow
import eam.edu.co.ingesoft.prestamoLibro.model.entities.User
import eam.edu.co.ingesoft.prestamoLibro.service.BorrowService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.util.*
import javax.persistence.EntityManager

@SpringBootTest
@Transactional
class BorrowServiceTest {
    @Autowired
    lateinit var entityManager: EntityManager

    @Autowired
    lateinit var borrowService: BorrowService

    @Test
    fun listaPrestamoDeUnUsuarioHappyPathTest() {
        val user = User("1", "rodriguez", "javier")
        entityManager.persist(user)
        val publisher = Publisher(2, "Castellana")
        entityManager.persist(publisher)
        val book = Book("1", "178643438", 10, "Matematica Vectorial", publisher)
        entityManager.persist(book)
        entityManager.persist(Borrow(1L, Date(2021, 9, 23), user, book))
        entityManager.persist(Borrow(2L, Date(2021, 9, 23), user, book))
        entityManager.persist(Borrow(3L, Date(2021, 9, 23), user, book))
        entityManager.persist(Borrow(4L, Date(2021, 9, 23), user, book))

        val products = borrowService.listaPrestamoDeUnUsuario("1")

        Assertions.assertEquals(4, products.size)
    }

    @Test
    fun listaPrestamoDeUnUsuarioNotFoundTest() {
        val user = User("1", "rodriguez", "javier")
        entityManager.persist(user)
        val publisher = Publisher(2, "Castellana")
        entityManager.persist(publisher)
        val book = Book("1", "178643438", 10, "Matematica Vectorial", publisher)
        entityManager.persist(book)
        val borrow = Borrow(1L, Date(2021, 9, 23), user, book)
        entityManager.persist(borrow)
        val exc = Assertions.assertThrows(
            BusinessException::class.java,
            {
                borrowService.listaPrestamoDeUnUsuario("2")
            }
        )
        Assertions.assertEquals("The user does not exist", exc.message)
    }

    @Test
    fun prestarLibroToUserNotFoundTest() {
        val user = User("1", "rodriguez", "javier")
        entityManager.persist(user)
        val publisher = Publisher(2, "Castellana")
        entityManager.persist(publisher)
        val book = Book("1", "178643438", 10, "Matematica Vectorial", publisher)
        entityManager.persist(book)
        val borrow = Borrow(1L, Date(2021, 9, 23), user, book)
        entityManager.persist(borrow)
        val exc = Assertions.assertThrows(
            BusinessException::class.java,
            {
                borrowService.prestarLibro(borrow, "2", "1")
            }
        )
        Assertions.assertEquals("The user does not exist", exc.message)
    }

    @Test
    fun prestarLibroToBookNotFoundTest() {
        val user = User("1", "rodriguez", "javier")
        entityManager.persist(user)
        val publisher = Publisher(2, "Castellana")
        entityManager.persist(publisher)
        val book = Book("1", "178643438", 10, "Matematica Vectorial", publisher)
        entityManager.persist(book)
        val borrow = Borrow(1L, Date(2021, 9, 23), user, book)
        entityManager.persist(borrow)
        val exc = Assertions.assertThrows(
            BusinessException::class.java,
            {
                borrowService.prestarLibro(borrow, "1", "4")
            }
        )
        Assertions.assertEquals("The book does not exist", exc.message)
    }

    @Test
    fun prestarLibroToBookCantidadNotFoundTest() {
        val user = User("1", "rodriguez", "javier")
        entityManager.persist(user)
        val publisher = Publisher(2, "Castellana")
        entityManager.persist(publisher)
        val book = Book("1", "178643438", 0, "Matematica Vectorial", publisher)
        entityManager.persist(book)
        val borrow = Borrow(1L, Date(2021, 9, 23), user, book)
        entityManager.persist(borrow)
        val exc = Assertions.assertThrows(
            BusinessException::class.java,
            {
                borrowService.prestarLibro(borrow, "1", "1")
            }
        )
        Assertions.assertEquals("No hay ejemplares de este libro para prestar", exc.message)
    }

    @Test
    fun prestarLibroCuandoHayMasDe5LibrosPrestados() {
        val user = User("1", "rodriguez", "javier")
        entityManager.persist(user)
        val publisher = Publisher(2, "Castellana")
        entityManager.persist(publisher)
        val book = Book("1", "178643438", 10, "Matematica Vectorial", publisher)
        entityManager.persist(book)

        for (i in 1..6) {
            entityManager.persist(
                Borrow(
                i.toLong(),
                Date(2021, 9, 23),
                user,
                book)
            )
        }

        val exc = Assertions.assertThrows(
            BusinessException::class.java
        ) {
            borrowService.prestarLibro(Borrow(1L,Date(2021, 9, 23),user,book),"1","1")
        }

        Assertions.assertEquals("Solo 5 prestamos por persona", exc.message)
    }

    @Test
    fun prestarLibroToPersonHappyPathTest() {
        val user = User("1", "rodriguez", "javier")
        entityManager.persist(user)
        val publisher = Publisher(2, "Castellana")
        entityManager.persist(publisher)
        val book = Book("1", "178643438", 10, "Matematica Vectorial", publisher)
        entityManager.persist(book)
        val borrow = Borrow(1L, Date(2021, 9, 23), user, book)
        entityManager.persist(borrow)

        borrowService.prestarLibro(borrow, "1", "1")

        val prest = entityManager.find(Borrow::class.java, 1L)

        Assertions.assertNotNull(prest)
        Assertions.assertEquals("javier", prest.id_user.nombre_usuario)
        Assertions.assertEquals("rodriguez", prest.id_user.apellido_usuario)
        Assertions.assertEquals(9, prest.book.cantidad)
    }

}