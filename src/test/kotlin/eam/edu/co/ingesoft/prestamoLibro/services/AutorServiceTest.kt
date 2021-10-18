package eam.edu.co.ingesoft.prestamoLibro.services

import eam.edu.co.ingesoft.prestamoLibro.exception.BusinessException
import eam.edu.co.ingesoft.prestamoLibro.model.Autor
import eam.edu.co.ingesoft.prestamoLibro.service.AutorService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@SpringBootTest
@Transactional
class AutorServiceTest {
    @Autowired
    lateinit var entityManager: EntityManager

    @Autowired
    lateinit var autorService: AutorService

    @Test
    fun createAutorHappyPathTest(){
        autorService.createAutor(Autor(2L, "juan", "marin"))

        val autorToAssert = entityManager.find(Autor::class.java, 2L)
        Assertions.assertNotNull(autorToAssert)
        Assertions.assertEquals("juan", autorToAssert.name)
        Assertions.assertEquals("marin", autorToAssert.lastname)
    }

    @Test
    fun createAutorAlreadyExistsTest(){
        //Prereqquisitos
        entityManager.persist(Autor(2L, "juan", "marin"))

        try {
            autorService.createAutor(Autor(2L, "juan", "marin"))
            Assertions.fail()
        } catch (e: BusinessException) {
            Assertions.assertEquals("This autor already exists", e.message)
        }
    }
}