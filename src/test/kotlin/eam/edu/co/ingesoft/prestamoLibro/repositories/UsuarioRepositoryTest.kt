package eam.edu.co.ingesoft.prestamoLibro.repositories

import eam.edu.co.ingesoft.prestamoLibro.model.Usuario
import eam.edu.co.ingesoft.prestamoLibro.repository.UsuarioRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@SpringBootTest
@Transactional
class UsuarioRepositoryTest {
    @Autowired
    lateinit var usuarioRepository: UsuarioRepository

    @Autowired
    lateinit var entityManager: EntityManager

    @Test
    fun contextLoads() {
    }

    @Test
    fun testCreate() {
        usuarioRepository.create(Usuario("1", "Rodriguez", "Javier"))

        val usuario = entityManager.find(Usuario::class.java,"1")
        Assertions.assertNotNull(usuario)
        Assertions.assertEquals("Javier", usuario.nombre_usuario)
        Assertions.assertEquals("Rodriguez", usuario.apellido_usuario)
    }

    @Test
    fun testUpdate() {
        //prerequisito
        entityManager.persist(Usuario("1","Rodriguez","Javier"))

        //ejecutando...
        val usuario = entityManager.find(Usuario::class.java, "1")
        usuario.nombre_usuario = "Sandra"
        usuario.apellido_usuario = "Marulanda"

        usuarioRepository.update(usuario)

        //assersiones
        val usuarioAssert = entityManager.find(Usuario::class.java, "1")
        Assertions.assertEquals("Sandra", usuarioAssert.nombre_usuario)
        Assertions.assertEquals("Marulanda", usuarioAssert.apellido_usuario)
    }

    @Test
    fun findTest() {
        entityManager.persist(Usuario("1","Rodriguez","Javier"))

        val usuario = usuarioRepository.find("1")

        Assertions.assertNotNull(usuario)
        Assertions.assertEquals("Javier", usuario?.nombre_usuario)
        Assertions.assertEquals("Rodriguez", usuario?.apellido_usuario)

    }

    @Test
    fun testDelete() {
        entityManager.persist(Usuario("1","Rodriguez","Javier"))
        //ejecucion de la preuba
        usuarioRepository.delete("1")

        //assersiones
        val usuario = entityManager.find(Usuario::class.java, "1")
        Assertions.assertNull(usuario)
    }
}