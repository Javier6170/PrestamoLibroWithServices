package eam.edu.co.ingesoft.prestamoLibro.repositories

import eam.edu.co.ingesoft.prestamoLibro.model.entities.User
import eam.edu.co.ingesoft.prestamoLibro.repository.UserRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@SpringBootTest
@Transactional
class UserRepositoryTest {
    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var entityManager: EntityManager

    @Test
    fun contextLoads() {
    }

    @Test
    fun testCreate() {
        userRepository.create(User("1", "Rodriguez", "Javier"))

        val user = entityManager.find(User::class.java,"1")
        Assertions.assertNotNull(user)
        Assertions.assertEquals("Javier", user.nombre_usuario)
        Assertions.assertEquals("Rodriguez", user.apellido_usuario)
    }

    @Test
    fun testUpdate() {
        //prerequisito
        entityManager.persist(User("1","Rodriguez","Javier"))

        //ejecutando...
        val user = entityManager.find(User::class.java, "1")
        user.nombre_usuario = "Sandra"
        user.apellido_usuario = "Marulanda"

        userRepository.update(user)

        //assersiones
        val userAssert = entityManager.find(User::class.java, "1")
        Assertions.assertEquals("Sandra", userAssert.nombre_usuario)
        Assertions.assertEquals("Marulanda", userAssert.apellido_usuario)
    }

    @Test
    fun findTest() {
        entityManager.persist(User("1","Rodriguez","Javier"))

        val usuario = userRepository.find("1")

        Assertions.assertNotNull(usuario)
        Assertions.assertEquals("Javier", usuario?.nombre_usuario)
        Assertions.assertEquals("Rodriguez", usuario?.apellido_usuario)

    }

    @Test
    fun testDelete() {
        entityManager.persist(User("1","Rodriguez","Javier"))
        //ejecucion de la preuba
        userRepository.delete("1")

        //assersiones
        val user = entityManager.find(User::class.java, "1")
        Assertions.assertNull(user)
    }
}