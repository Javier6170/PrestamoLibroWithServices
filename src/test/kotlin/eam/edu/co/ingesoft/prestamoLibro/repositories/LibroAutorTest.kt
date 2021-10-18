package eam.edu.co.ingesoft.prestamoLibro.repositories

import eam.edu.co.ingesoft.prestamoLibro.model.Autor
import eam.edu.co.ingesoft.prestamoLibro.model.Editorial
import eam.edu.co.ingesoft.prestamoLibro.model.Libro
import eam.edu.co.ingesoft.prestamoLibro.model.Libro_autor
import eam.edu.co.ingesoft.prestamoLibro.repository.LibroAutorRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@SpringBootTest
@Transactional
class LibroAutorTest {
    @Autowired
    lateinit var libroAutorRepository: LibroAutorRepository

    @Autowired
    lateinit var entityManager: EntityManager

    @Test
    fun contextLoads() {
    }

    @Test
    fun testCreate() {
        libroAutorRepository.create(Libro_autor(1L, Autor(2, "juan", "marin"),
            Libro("1","178643438",10,"Matematica Vectorial", Editorial(2,"Castellana"))
        ))

        val libroAutor = entityManager.find(Libro_autor::class.java,1L)
        Assertions.assertNotNull(libroAutor)
        Assertions.assertEquals(1L, libroAutor.id)
        Assertions.assertEquals("Matematica Vectorial", libroAutor.libro.nombre_Libro)
        Assertions.assertEquals("Castellana", libroAutor.libro.id_editorial.nombre_editorial)
    }

    @Test
    fun testUpdate() {
        //prerequisito
        entityManager.persist(Libro_autor(1L, Autor(2, "juan", "marin"),Libro("1","178643438",10,"Matematica Vectorial",Editorial(2,"Castellana"))))

        //ejecutando...
        val libroAutor = entityManager.find(Libro_autor::class.java, 1L)
        libroAutor.libro.isbn_libro = "1020-4040-89871"
        libroAutor.libro.nombre_Libro = "Castellano basico"
        libroAutor.libro.id_editorial.nombre_editorial = "Babel"
        libroAutor.autor.name = "javier"

        libroAutorRepository.update(libroAutor)

        //assersiones
        val libroAutorAssert = entityManager.find(Libro_autor::class.java, 1L)
        Assertions.assertEquals("1020-4040-89871", libroAutorAssert.libro.isbn_libro)
        Assertions.assertEquals("Castellano basico", libroAutorAssert.libro.nombre_Libro)
        Assertions.assertEquals("Babel", libroAutorAssert.libro.id_editorial.nombre_editorial)
        Assertions.assertEquals("javier", libroAutorAssert.autor.name)
    }

    @Test
    fun findTest() {
        entityManager.persist(Libro_autor(1L, Autor(2, "juan", "marin"),Libro("1","178643438",10,"Matematica Vectorial",Editorial(2,"Castellana"))))

        val libroAutor = libroAutorRepository.find(1L)

        Assertions.assertNotNull(libroAutor)
        Assertions.assertEquals("Matematica Vectorial", libroAutor?.libro?.nombre_Libro)
        Assertions.assertEquals("Castellana", libroAutor?.libro?.id_editorial?.nombre_editorial)
        Assertions.assertEquals("178643438", libroAutor?.libro?.isbn_libro)
        Assertions.assertEquals("juan", libroAutor?.autor?.name)
    }


    @Test
    fun testDelete() {
        entityManager.persist(Libro_autor(1L, Autor(2, "juan", "marin"),Libro("1","178643438",10,"Matematica Vectorial",Editorial(2,"Castellana"))))

        //ejecucion de la preuba
        libroAutorRepository.delete(1L)

        //assersiones
        val libroAutor = entityManager.find(Libro_autor::class.java, 1L)
        Assertions.assertNull(libroAutor)
    }

    @Test
    fun findByAutor(){
        val autor = Autor(2L, "juan", "marin")
        entityManager.persist(autor)
        val editorial = Editorial(2L,"Castellana")
        entityManager.persist(editorial)
        val libro = Libro("1","178643438",10,"Matematica Vectorial",editorial)
        entityManager.persist(libro)
        val libro2 = Libro("2","6562648465",10,"ingles avanzado",editorial)
        entityManager.persist(libro2)
        val libro3 = Libro("3","6465165498",10,"Introduccion a los lenguajes de programacion",editorial)
        entityManager.persist(libro3)

        entityManager.persist(Libro_autor(1L, autor,libro))
        entityManager.persist(Libro_autor(2L, autor,libro2))
        entityManager.persist(Libro_autor(3L, autor,libro3))

        //ejecutando pruebas
        val autores = libroAutorRepository.findByAutor(2L)

        //assertions
        Assertions.assertEquals(3,autores.size)
    }

    @Test
    fun findByLibro(){
        val autor = Autor(2L, "juan", "marin")
        entityManager.persist(autor)
        val autor2 = Autor(3L, "santiago", "gutierrez")
        entityManager.persist(autor2)
        val autor3 = Autor(4L, "felipe", "garcia")
        entityManager.persist(autor3)
        val editorial = Editorial(2L,"Castellana")
        entityManager.persist(editorial)
        val libro = Libro("1","178643438",10,"Matematica Vectorial",editorial)
        entityManager.persist(libro)
        entityManager.persist(Libro_autor(1L, autor,libro))
        entityManager.persist(Libro_autor(2L, autor2,libro))
        entityManager.persist(Libro_autor(3L, autor3,libro))

        //ejecutando pruebas
        val libros = libroAutorRepository.findByLibro("1")

        //assertions
        Assertions.assertEquals(3,libros.size)
    }
}