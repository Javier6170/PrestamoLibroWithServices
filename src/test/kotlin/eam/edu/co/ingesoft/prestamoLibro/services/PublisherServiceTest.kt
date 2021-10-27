package eam.edu.co.ingesoft.prestamoLibro.services

import eam.edu.co.ingesoft.prestamoLibro.exception.BusinessException
import eam.edu.co.ingesoft.prestamoLibro.model.entities.Publisher
import eam.edu.co.ingesoft.prestamoLibro.service.PublisherService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@SpringBootTest
@Transactional
class PublisherServiceTest {
    @Autowired
    lateinit var entityManager: EntityManager

    @Autowired
    lateinit var publisherService: PublisherService

    @Test
    fun createUserHappyPathTest(){
        publisherService.createEditorial(Publisher(1L, "castellana"))

        val publisherToAssert = entityManager.find(Publisher::class.java, 1L)
        Assertions.assertNotNull(publisherToAssert)
        Assertions.assertEquals("castellana", publisherToAssert.nombre_editorial)
    }

    @Test
    fun createUserAlreadyExistsTest(){
        //Prereqquisitos
        entityManager.persist(Publisher(1L, "castellana"))

        try {
            publisherService.createEditorial(Publisher(1L, "castellana"))
            Assertions.fail()
        } catch (e: BusinessException) {
            Assertions.assertEquals("This editorial already exists", e.message)
        }
    }
}