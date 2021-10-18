package eam.edu.co.ingesoft.prestamoLibro.repositories

import eam.edu.co.ingesoft.prestamoLibro.model.Editorial
import eam.edu.co.ingesoft.prestamoLibro.repository.EditorialRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@SpringBootTest
@Transactional
class EditorialRepositoryTest {
    @Autowired
    lateinit var editorialRepository: EditorialRepository

    @Autowired
    lateinit var entityManager: EntityManager

    @Test
    fun contextLoads() {
    }

    @Test
    fun testCreate() {
        editorialRepository.create(Editorial(1L, "castellana"))

        val editorial = entityManager.find(Editorial::class.java,1L)
        Assertions.assertNotNull(editorial)
        Assertions.assertEquals("castellana", editorial.nombre_editorial)
    }

    @Test
    fun testUpdate() {
        //prerequisito
        entityManager.persist(Editorial(1L,"castellana"))

        //ejecutando...
        val editorial = entityManager.find(Editorial::class.java, 1L)
        editorial.nombre_editorial = "babel"

        editorialRepository.update(editorial)

        //assersiones
        val editorialAssert = entityManager.find(Editorial::class.java, 1L)
        Assertions.assertEquals("babel", editorialAssert.nombre_editorial)
    }

    @Test
    fun findTest() {
        entityManager.persist(Editorial(1L,"castellana"))

        val editorial = editorialRepository.find(1L)

        Assertions.assertNotNull(editorial)
        Assertions.assertEquals("castellana", editorial?.nombre_editorial)

    }

    @Test
    fun testDelete() {
        entityManager.persist(Editorial(1L,"castellana"))
        //ejecucion de la preuba
        editorialRepository.delete(1L)

        //assersiones
        val editorial = entityManager.find(Editorial::class.java, 1L)
        Assertions.assertNull(editorial)
    }
}