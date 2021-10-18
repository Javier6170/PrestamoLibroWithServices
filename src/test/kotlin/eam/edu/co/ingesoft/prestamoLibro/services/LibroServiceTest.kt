package eam.edu.co.ingesoft.prestamoLibro.services

import eam.edu.co.ingesoft.prestamoLibro.exception.BusinessException
import eam.edu.co.ingesoft.prestamoLibro.model.Editorial
import eam.edu.co.ingesoft.prestamoLibro.model.Libro
import eam.edu.co.ingesoft.prestamoLibro.service.LibroService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@SpringBootTest
@Transactional
class LibroServiceTest {
    @Autowired
    lateinit var entityManager: EntityManager

    @Autowired
    lateinit var libroService: LibroService

    @Test
    fun createUserHappyPathTest() {
        val editorial = Editorial(2, "Castellana")
        entityManager.persist(editorial)
        libroService.createlibro(Libro("1", "178643438", 10, "Matematica Vectorial", editorial))

        val libroToAssert = entityManager.find(Libro::class.java, "1")
        Assertions.assertNotNull(libroToAssert)
        Assertions.assertEquals("Matematica Vectorial", libroToAssert.nombre_Libro)
    }

    @Test
    fun createUserAlreadyExistsIdandNameTest() {
        //Prereqquisitos
        val editorial = Editorial(2, "Castellana")
        entityManager.persist(editorial)
        entityManager.persist(Libro("1", "178643438", 10, "Matematica Vectorial", editorial))

        try {
            libroService.createlibro(Libro("1", "178643438", 10, "Matematica Vectorial", editorial))
            Assertions.fail()
        } catch (e: BusinessException) {
            Assertions.assertEquals("This libro already exists", e.message)
        }
    }

    @Test
    fun entregarLibroTestHappyPath(){
        val editorial = Editorial(2, "Castellana")
        entityManager.persist(editorial)
        entityManager.persist(Libro("1", "178643438", 10, "Matematica Vectorial", editorial))

        libroService.entregarLibro(Libro("1", "178643438", 10, "Matematica Vectorial", editorial))

        val libroAssert = entityManager.find(Libro::class.java, "1")
        Assertions.assertEquals(11, libroAssert.cantidad)
    }

    @Test
    fun entregarLibroNotExists(){
        val editorial = Editorial(2, "Castellana")
        entityManager.persist(editorial)

        val exception = Assertions.assertThrows(
            BusinessException::class.java,
            { libroService.entregarLibro(Libro("1", "178643438", 10, "Matematica Vectorial", editorial)) }
        )

        Assertions.assertEquals("This book does not exists", exception.message)
    }
}