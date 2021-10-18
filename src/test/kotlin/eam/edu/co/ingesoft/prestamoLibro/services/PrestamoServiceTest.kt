package eam.edu.co.ingesoft.prestamoLibro.services

import eam.edu.co.ingesoft.prestamoLibro.exception.BusinessException
import eam.edu.co.ingesoft.prestamoLibro.model.Editorial
import eam.edu.co.ingesoft.prestamoLibro.model.Libro
import eam.edu.co.ingesoft.prestamoLibro.model.Prestamo
import eam.edu.co.ingesoft.prestamoLibro.model.Usuario
import eam.edu.co.ingesoft.prestamoLibro.service.PrestamoService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.util.*
import javax.persistence.EntityManager

@SpringBootTest
@Transactional
class PrestamoServiceTest {
    @Autowired
    lateinit var entityManager: EntityManager

    @Autowired
    lateinit var prestamoService: PrestamoService

    @Test
    fun listaPrestamoDeUnUsuarioHappyPathTest() {
        val usuario = Usuario("1", "rodriguez", "javier")
        entityManager.persist(usuario)
        val editorial = Editorial(2, "Castellana")
        entityManager.persist(editorial)
        val libro = Libro("1", "178643438", 10, "Matematica Vectorial", editorial)
        entityManager.persist(libro)
        entityManager.persist(Prestamo(1L, Date(2021, 9, 23), usuario, libro))
        entityManager.persist(Prestamo(2L, Date(2021, 9, 23), usuario, libro))
        entityManager.persist(Prestamo(3L, Date(2021, 9, 23), usuario, libro))
        entityManager.persist(Prestamo(4L, Date(2021, 9, 23), usuario, libro))

        val products = prestamoService.listaPrestamoDeUnUsuario("1")

        Assertions.assertEquals(4, products.size)
    }

    @Test
    fun listaPrestamoDeUnUsuarioNotFoundTest() {
        val usuario = Usuario("1", "rodriguez", "javier")
        entityManager.persist(usuario)
        val editorial = Editorial(2, "Castellana")
        entityManager.persist(editorial)
        val libro = Libro("1", "178643438", 10, "Matematica Vectorial", editorial)
        entityManager.persist(libro)
        val prestamo = Prestamo(1L, Date(2021, 9, 23), usuario, libro)
        entityManager.persist(prestamo)
        val exc = Assertions.assertThrows(
            BusinessException::class.java,
            {
                prestamoService.listaPrestamoDeUnUsuario("2")
            }
        )
        Assertions.assertEquals("The user does not exist", exc.message)
    }

    @Test
    fun prestarLibroToUserNotFoundTest() {
        val usuario = Usuario("1", "rodriguez", "javier")
        entityManager.persist(usuario)
        val editorial = Editorial(2, "Castellana")
        entityManager.persist(editorial)
        val libro = Libro("1", "178643438", 10, "Matematica Vectorial", editorial)
        entityManager.persist(libro)
        val prestamo = Prestamo(1L, Date(2021, 9, 23), usuario, libro)
        entityManager.persist(prestamo)
        val exc = Assertions.assertThrows(
            BusinessException::class.java,
            {
                prestamoService.prestarLibro(prestamo, "2", "1")
            }
        )
        Assertions.assertEquals("The user does not exist", exc.message)
    }

    @Test
    fun prestarLibroToBookNotFoundTest() {
        val usuario = Usuario("1", "rodriguez", "javier")
        entityManager.persist(usuario)
        val editorial = Editorial(2, "Castellana")
        entityManager.persist(editorial)
        val libro = Libro("1", "178643438", 10, "Matematica Vectorial", editorial)
        entityManager.persist(libro)
        val prestamo = Prestamo(1L, Date(2021, 9, 23), usuario, libro)
        entityManager.persist(prestamo)
        val exc = Assertions.assertThrows(
            BusinessException::class.java,
            {
                prestamoService.prestarLibro(prestamo, "1", "4")
            }
        )
        Assertions.assertEquals("The book does not exist", exc.message)
    }

    @Test
    fun prestarLibroToBookCantidadNotFoundTest() {
        val usuario = Usuario("1", "rodriguez", "javier")
        entityManager.persist(usuario)
        val editorial = Editorial(2, "Castellana")
        entityManager.persist(editorial)
        val libro = Libro("1", "178643438", 0, "Matematica Vectorial", editorial)
        entityManager.persist(libro)
        val prestamo = Prestamo(1L, Date(2021, 9, 23), usuario, libro)
        entityManager.persist(prestamo)
        val exc = Assertions.assertThrows(
            BusinessException::class.java,
            {
                prestamoService.prestarLibro(prestamo, "1", "1")
            }
        )
        Assertions.assertEquals("No hay ejemplares de este libro para prestar", exc.message)
    }

    @Test
    fun prestarLibroCuandoHayMasDe5LibrosPrestados() {
        val usuario = Usuario("1", "rodriguez", "javier")
        entityManager.persist(usuario)
        val editorial = Editorial(2, "Castellana")
        entityManager.persist(editorial)
        val libro = Libro("1", "178643438", 10, "Matematica Vectorial", editorial)
        entityManager.persist(libro)

        for (i in 1..6) {
            entityManager.persist(Prestamo(
                i.toLong(),
                Date(2021, 9, 23),
                usuario,
                libro))
        }

        val exc = Assertions.assertThrows(
            BusinessException::class.java
        ) {
            prestamoService.prestarLibro(Prestamo(1L,Date(2021, 9, 23),usuario,libro),"1","1")
        }

        Assertions.assertEquals("Solo 5 prestamos por persona", exc.message)
    }

    @Test
    fun prestarLibroToPersonHappyPathTest() {
        val usuario = Usuario("1", "rodriguez", "javier")
        entityManager.persist(usuario)
        val editorial = Editorial(2, "Castellana")
        entityManager.persist(editorial)
        val libro = Libro("1", "178643438", 10, "Matematica Vectorial", editorial)
        entityManager.persist(libro)
        val prestamo = Prestamo(1L, Date(2021, 9, 23), usuario, libro)
        entityManager.persist(prestamo)

        prestamoService.prestarLibro(prestamo, "1", "1")

        val prest = entityManager.find(Prestamo::class.java, 1L)

        Assertions.assertNotNull(prest)
        Assertions.assertEquals("javier", prest.id_usuario.nombre_usuario)
        Assertions.assertEquals("rodriguez", prest.id_usuario.apellido_usuario)
        Assertions.assertEquals(9, prest.libro.cantidad)
    }

}