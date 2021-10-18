package eam.edu.co.ingesoft.prestamoLibro.services

import eam.edu.co.ingesoft.prestamoLibro.model.Autor
import eam.edu.co.ingesoft.prestamoLibro.model.Editorial
import eam.edu.co.ingesoft.prestamoLibro.model.Libro
import eam.edu.co.ingesoft.prestamoLibro.model.Libro_autor
import eam.edu.co.ingesoft.prestamoLibro.service.Libro_autorService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@SpringBootTest
@Transactional
class LibroAutorServiceTest {
    @Autowired
    lateinit var entityManager: EntityManager

    @Autowired
    lateinit var libro_autorService: Libro_autorService

    @Test
    fun listCategoryByProduct(){
        val autor = Autor(2L, "juan", "marin")
        entityManager.persist(autor)
        val editorial = Editorial(2,"Castellana")
        entityManager.persist(editorial)
        val libro = Libro("1","178643438",10,"Matematica Vectorial",editorial)
        entityManager.persist(libro)
        entityManager.persist(Libro_autor(2L, autor,libro))
        entityManager.persist(Libro_autor(3L, autor,libro))
        entityManager.persist(Libro_autor(4L, autor,libro))
        entityManager.persist(Libro_autor(5L, autor,libro))

        val categorys = libro_autorService.listAutoryByAutorBook(2L)

        Assertions.assertEquals(4,categorys.size)
    }
}