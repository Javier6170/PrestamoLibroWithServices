package eam.edu.co.ingesoft.prestamoLibro.repositories

import eam.edu.co.ingesoft.prestamoLibro.model.entities.Publisher
import eam.edu.co.ingesoft.prestamoLibro.model.entities.Book
import eam.edu.co.ingesoft.prestamoLibro.model.entities.Borrow
import eam.edu.co.ingesoft.prestamoLibro.model.entities.User
import eam.edu.co.ingesoft.prestamoLibro.repository.BorrowRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.util.*
import javax.persistence.EntityManager

@SpringBootTest
@Transactional
class BorrowRepositoryTest {
    @Autowired
    lateinit var prestamoRepository: BorrowRepository

    @Autowired
    lateinit var entityManager: EntityManager

    @Test
    fun contextLoads() {
    }

    @Test
    fun testCreate() {
        prestamoRepository.create(
            Borrow(1L,
            Date(23,9,2021),
                User("1","rodriguez","javier"),
                Book("1","178643438",10,"Matematica Vectorial", Publisher(2,"Castellana"))
            )
        )

        val borrow = entityManager.find(Borrow::class.java,1L)
        Assertions.assertNotNull(borrow)
        Assertions.assertEquals("Matematica Vectorial", borrow.book.nombre_Libro)
        Assertions.assertEquals("178643438", borrow.book.isbn_libro)
        Assertions.assertEquals(Date(23,9,2021), borrow.fecha_prestamo)
        Assertions.assertEquals("Castellana", borrow.book.id_publisher.nombre_editorial)
        Assertions.assertEquals("javier", borrow.id_user.nombre_usuario)
        Assertions.assertEquals("rodriguez", borrow.id_user.apellido_usuario)
    }

    @Test
    fun testUpdate() {
        //prerequisito
        entityManager.persist(
            Borrow(1L,
            Date(2021,9,23),
                User("1","rodriguez","javier"),
                Book("1","178643438",10,"Matematica Vectorial", Publisher(2,"Castellana"))
            )
        )

        //ejecutando...
        val borrow = entityManager.find(Borrow::class.java, 1L)
        borrow.id_user.nombre_usuario = "gladys"
        borrow.id_user.apellido_usuario = "martin"
        borrow.book.isbn_libro = "1020-4040-89871"
        borrow.book.nombre_Libro = "Castellano basico"
        borrow.book.id_publisher.nombre_editorial = "Nacho"

        prestamoRepository.update(borrow)

        //assersiones
        val borrowAssert = entityManager.find(Borrow::class.java, 1L)
        Assertions.assertEquals("gladys", borrowAssert.id_user.nombre_usuario)
        Assertions.assertEquals("martin", borrowAssert.id_user.apellido_usuario)
        Assertions.assertEquals("1020-4040-89871",  borrow.book.isbn_libro)
        Assertions.assertEquals("Castellano basico",  borrow.book.nombre_Libro)
        Assertions.assertEquals("Nacho",  borrow.book.id_publisher.nombre_editorial)
    }

    @Test
    fun findTest() {
        entityManager.persist(
            Borrow(1L,
            Date(2021,9,23),
                User("1","rodriguez","javier"),
                Book("1","178643438",10,"Matematica Vectorial", Publisher(2,"Castellana"))
            )
        )

        val prestamo = prestamoRepository.find(1L)

        Assertions.assertNotNull(prestamo)
        Assertions.assertEquals("javier", prestamo?.id_user?.nombre_usuario)
        Assertions.assertEquals("rodriguez", prestamo?.id_user?.apellido_usuario)
        Assertions.assertEquals("Matematica Vectorial", prestamo?.book?.nombre_Libro)
    }

    @Test
    fun testDelete() {
        entityManager.persist(
            Borrow(1L,
            Date(2021,9,23),
                User("1","rodriguez","javier"),
                Book("1","178643438",10,"Matematica Vectorial", Publisher(2,"Castellana"))
            )
        )

        //ejecucion de la preuba
        prestamoRepository.delete(1L)

        //assersiones
        val borrow = entityManager.find(Borrow::class.java, 1L)
        Assertions.assertNull(borrow)
    }

    @Test
    fun testFindByUsuario(){
        val user = User("1", "Rodriguez", "Javier")
        entityManager.persist(user)
        val publisher = Publisher(2L,"Castellana")
        entityManager.persist(publisher)
        val book = Book("1","178643438",10,"Matematica Vectorial",publisher)
        entityManager.persist(book)
        entityManager.persist(Borrow(1L, Date(2021,9,25),user,book))
        entityManager.persist(Borrow(2L, Date(2021,9,26),user,book))
        entityManager.persist(Borrow(3L, Date(2021,9,27),user,book))

        //ejecutando pruebas
        val usuarios = prestamoRepository.findByUsuario("1")

        //assertions
        Assertions.assertEquals(3,usuarios.size)
    }

    @Test
    fun testFindByLibro(){
        val user = User("1", "Rodriguez", "Javier")
        entityManager.persist(user)
        val publisher = Publisher(2L,"Castellana")
        entityManager.persist(publisher)
        val book = Book("1","178643438",10,"Matematica Vectorial",publisher)
        entityManager.persist(book)
        entityManager.persist(Borrow(1L, Date(2021,9,25),user,book))
        entityManager.persist(Borrow(2L, Date(2021,9,26),user,book))


        //ejecutando pruebas
        val libros = prestamoRepository.findByLibro("1")

        //assertions
        Assertions.assertEquals(2,libros.size)
    }
}