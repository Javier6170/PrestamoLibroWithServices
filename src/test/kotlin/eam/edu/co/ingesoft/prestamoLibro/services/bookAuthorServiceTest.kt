package eam.edu.co.ingesoft.prestamoLibro.services

import eam.edu.co.ingesoft.prestamoLibro.model.entities.Author
import eam.edu.co.ingesoft.prestamoLibro.model.entities.Publisher
import eam.edu.co.ingesoft.prestamoLibro.model.entities.Book
import eam.edu.co.ingesoft.prestamoLibro.model.entities.AuthorBook
import eam.edu.co.ingesoft.prestamoLibro.service.AuthorBookService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@SpringBootTest
@Transactional
class bookAuthorServiceTest {
    @Autowired
    lateinit var entityManager: EntityManager

    @Autowired
    lateinit var authorBookService: AuthorBookService

    @Test
    fun listCategoryByProduct(){
        val author = Author(2L, "juan", "marin")
        entityManager.persist(author)
        val publisher = Publisher(2,"Castellana")
        entityManager.persist(publisher)
        val book = Book("1","178643438",10,"Matematica Vectorial",publisher)
        entityManager.persist(book)
        entityManager.persist(AuthorBook(2L, author,book))
        entityManager.persist(AuthorBook(3L, author,book))
        entityManager.persist(AuthorBook(4L, author,book))
        entityManager.persist(AuthorBook(5L, author,book))

        val categorys = authorBookService.listAutoryByAutorBook(2L)

        Assertions.assertEquals(4,categorys.size)
    }
}