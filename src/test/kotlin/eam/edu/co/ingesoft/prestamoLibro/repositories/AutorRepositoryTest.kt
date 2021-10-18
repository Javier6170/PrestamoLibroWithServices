package eam.edu.co.ingesoft.prestamoLibro.repositories

import eam.edu.co.ingesoft.prestamoLibro.model.Autor
import eam.edu.co.ingesoft.prestamoLibro.repository.AutorRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@SpringBootTest
@Transactional
class AutorRepositoryTest {
    @Autowired
    lateinit var autorRepository: AutorRepository

    @Autowired
    lateinit var entityManager: EntityManager

    @Test
    fun contextLoads() {
    }

    @Test
    fun testCreate() {
        autorRepository.create(Autor(2L, "juan", "marin"))

        val autor = entityManager.find(Autor::class.java,2L)
        Assertions.assertNotNull(autor)
        Assertions.assertEquals("juan", autor.name)
        Assertions.assertEquals("marin", autor.lastname)
        Assertions.assertEquals(2L, autor.id)
    }

    @Test
    fun testUpdate() {
        //prerequisito
        entityManager.persist(Autor(2L,"juan","marin"))

        //ejecutando...
        val autor = entityManager.find(Autor::class.java, 2L)
        autor.name = "gladys"
        autor.lastname = "pepo"

        autorRepository.update(autor)

        //assersiones
        val autorAssert = entityManager.find(Autor::class.java, 2L)
        Assertions.assertEquals("gladys", autorAssert.name)
        Assertions.assertEquals("pepo", autorAssert.lastname)
    }

    @Test
    fun findTest() {
        entityManager.persist(Autor(2L,"juan","marin"))

        val autor = autorRepository.find(2L)

        Assertions.assertNotNull(autor)
        Assertions.assertEquals("juan", autor?.name)
        Assertions.assertEquals("marin", autor?.lastname)

    }

    @Test
    fun testDelete() {
        entityManager.persist(Autor(2L,"juan","marin"))
        //ejecucion de la preuba
        autorRepository.delete(2L)

        //assersiones
        val autor = entityManager.find(Autor::class.java, 2L)
        Assertions.assertNull(autor)
    }
}