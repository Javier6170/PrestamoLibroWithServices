package eam.edu.co.ingesoft.prestamoLibro.services

import eam.edu.co.ingesoft.prestamoLibro.exception.BusinessException
import eam.edu.co.ingesoft.prestamoLibro.model.Editorial
import eam.edu.co.ingesoft.prestamoLibro.service.EditorialService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@SpringBootTest
@Transactional
class EditorialServiceTest {
    @Autowired
    lateinit var entityManager: EntityManager

    @Autowired
    lateinit var editorialService: EditorialService

    @Test
    fun createUserHappyPathTest(){
        editorialService.createEditorial(Editorial(1L, "castellana"))

        val editorialToAssert = entityManager.find(Editorial::class.java, 1L)
        Assertions.assertNotNull(editorialToAssert)
        Assertions.assertEquals("castellana", editorialToAssert.nombre_editorial)
    }

    @Test
    fun createUserAlreadyExistsTest(){
        //Prereqquisitos
        entityManager.persist(Editorial(1L, "castellana"))

        try {
            editorialService.createEditorial(Editorial(1L, "castellana"))
            Assertions.fail()
        } catch (e: BusinessException) {
            Assertions.assertEquals("This editorial already exists", e.message)
        }
    }
}