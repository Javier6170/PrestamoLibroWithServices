package eam.edu.co.ingesoft.prestamoLibro.repositories

import eam.edu.co.ingesoft.prestamoLibro.model.entities.Publisher
import eam.edu.co.ingesoft.prestamoLibro.repository.PublisherRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@SpringBootTest
@Transactional
class PublisherRepositoryTest {
    @Autowired
    lateinit var publisherRepository: PublisherRepository

    @Autowired
    lateinit var entityManager: EntityManager

    @Test
    fun contextLoads() {
    }

    @Test
    fun testCreate() {
        publisherRepository.create(Publisher(1L, "castellana"))

        val publisher = entityManager.find(Publisher::class.java,1L)
        Assertions.assertNotNull(publisher)
        Assertions.assertEquals("castellana", publisher.nombre_editorial)
    }

    @Test
    fun testUpdate() {
        //prerequisito
        entityManager.persist(Publisher(1L,"castellana"))

        //ejecutando...
        val publisher = entityManager.find(Publisher::class.java, 1L)
        publisher.nombre_editorial = "babel"

        publisherRepository.update(publisher)

        //assersiones
        val publisherAssert = entityManager.find(Publisher::class.java, 1L)
        Assertions.assertEquals("babel", publisherAssert.nombre_editorial)
    }

    @Test
    fun findTest() {
        entityManager.persist(Publisher(1L,"castellana"))

        val editorial = publisherRepository.find(1L)

        Assertions.assertNotNull(editorial)
        Assertions.assertEquals("castellana", editorial?.nombre_editorial)

    }

    @Test
    fun testDelete() {
        entityManager.persist(Publisher(1L,"castellana"))
        //ejecucion de la preuba
        publisherRepository.delete(1L)

        //assersiones
        val publisher = entityManager.find(Publisher::class.java, 1L)
        Assertions.assertNull(publisher)
    }
}