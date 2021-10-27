package eam.edu.co.ingesoft.prestamoLibro.repositories

import eam.edu.co.ingesoft.prestamoLibro.model.entities.Author
import eam.edu.co.ingesoft.prestamoLibro.repository.AuthorRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@SpringBootTest
@Transactional
class AuthorRepositoryTest {
    @Autowired
    lateinit var authorRepository: AuthorRepository

    @Autowired
    lateinit var entityManager: EntityManager

    @Test
    fun contextLoads() {
    }

    @Test
    fun testCreate() {
        authorRepository.create(Author(2L, "juan", "marin"))

        val author = entityManager.find(Author::class.java,2L)
        Assertions.assertNotNull(author)
        Assertions.assertEquals("juan", author.name)
        Assertions.assertEquals("marin", author.lastname)
        Assertions.assertEquals(2L, author.id)
    }

    @Test
    fun testUpdate() {
        //prerequisito
        entityManager.persist(Author(2L,"juan","marin"))

        //ejecutando...
        val author = entityManager.find(Author::class.java, 2L)
        author.name = "gladys"
        author.lastname = "pepo"

        authorRepository.update(author)

        //assersiones
        val authorAssert = entityManager.find(Author::class.java, 2L)
        Assertions.assertEquals("gladys", authorAssert.name)
        Assertions.assertEquals("pepo", authorAssert.lastname)
    }

    @Test
    fun findTest() {
        entityManager.persist(Author(2L,"juan","marin"))

        val autor = authorRepository.find(2L)

        Assertions.assertNotNull(autor)
        Assertions.assertEquals("juan", autor?.name)
        Assertions.assertEquals("marin", autor?.lastname)

    }

    @Test
    fun testDelete() {
        entityManager.persist(Author(2L,"juan","marin"))
        //ejecucion de la preuba
        authorRepository.delete(2L)

        //assersiones
        val author = entityManager.find(Author::class.java, 2L)
        Assertions.assertNull(author)
    }
}