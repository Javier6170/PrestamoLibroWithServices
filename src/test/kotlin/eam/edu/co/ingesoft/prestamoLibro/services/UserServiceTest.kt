package eam.edu.co.ingesoft.prestamoLibro.services

import eam.edu.co.ingesoft.prestamoLibro.exception.BusinessException
import eam.edu.co.ingesoft.prestamoLibro.model.entities.User
import eam.edu.co.ingesoft.prestamoLibro.service.UserService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@SpringBootTest
@Transactional
class UserServiceTest {
    @Autowired
    lateinit var entityManager: EntityManager

    @Autowired
    lateinit var userService: UserService

    @Test
    fun createUserHappyPathTest(){
        userService.createUser(User("1", "Rodriguez", "Javier"))

        val userToAssert = entityManager.find(User::class.java, "1")
        Assertions.assertNotNull(userToAssert)
        Assertions.assertEquals("Javier", userToAssert.nombre_usuario)
        Assertions.assertEquals("Rodriguez", userToAssert.apellido_usuario)
    }

    @Test
    fun createUserAlreadyExistsTest(){
        //Prereqquisitos
        entityManager.persist(User("1", "Rodriguez", "Javier"))

        try {
            userService.createUser(User("1", "Rodriguez", "Javier"))
            Assertions.fail()
        } catch (e: BusinessException) {
            Assertions.assertEquals("This person already exists", e.message)
        }
    }
}