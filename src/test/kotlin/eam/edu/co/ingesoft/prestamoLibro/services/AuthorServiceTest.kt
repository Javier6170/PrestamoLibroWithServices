package eam.edu.co.ingesoft.prestamoLibro.services

import eam.edu.co.ingesoft.prestamoLibro.exception.BusinessException
import eam.edu.co.ingesoft.prestamoLibro.model.entities.Author
import eam.edu.co.ingesoft.prestamoLibro.service.AuthorService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@SpringBootTest
@Transactional
class AuthorServiceTest {
    @Autowired
    lateinit var entityManager: EntityManager

    @Autowired
    lateinit var authorService: AuthorService

    @Test
    fun createAutorHappyPathTest(){
        authorService.createAutor(Author(2L, "juan", "marin"))

        val authorToAssert = entityManager.find(Author::class.java, 2L)
        Assertions.assertNotNull(authorToAssert)
        Assertions.assertEquals("juan", authorToAssert.name)
        Assertions.assertEquals("marin", authorToAssert.lastname)
    }

    @Test
    fun createAutorAlreadyExistsTest(){
        //Prereqquisitos
        entityManager.persist(Author(2L, "juan", "marin"))

        try {
            authorService.createAutor(Author(2L, "juan", "marin"))
            Assertions.fail()
        } catch (e: BusinessException) {
            Assertions.assertEquals("This autor already exists", e.message)
        }
    }
}