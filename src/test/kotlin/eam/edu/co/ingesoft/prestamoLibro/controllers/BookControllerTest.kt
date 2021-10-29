package eam.edu.co.ingesoft.prestamoLibro.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import eam.edu.co.ingesoft.prestamoLibro.model.entities.Book
import eam.edu.co.ingesoft.prestamoLibro.model.entities.Borrow
import eam.edu.co.ingesoft.prestamoLibro.model.entities.Publisher
import eam.edu.co.ingesoft.prestamoLibro.model.entities.User
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.transaction.annotation.Transactional
import java.util.*
import javax.persistence.EntityManager

@SpringBootTest
@Transactional
//arrancar el servidor web
@AutoConfigureMockMvc
class BookControllerTest {
    //clase que simula el servidor web que expone las operaciones de los
    @Autowired
    lateinit var entityManager: EntityManager

    @Autowired
    lateinit var mocMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper



    @Test
    fun createBookHappyPathTest() {
        //prerequisitos..
        val publisher = Publisher(1, "Castellana")
        entityManager.persist(publisher)
        val body = """
           {
            "id": "6",
            "isbn_libro": "5454654",
            "cantidad": 10,
            "nombre_Libro": "Programacion web",
            "id_publisher":{
                "id": 1,
                "nombre_editorial": "Castellana"
            }
            }
        """.trimIndent()

        val request = MockMvcRequestBuilders
            .post("/books")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)

        val response = mocMvc.perform(request)
        val resp = response.andReturn().response
        //println(resp.contentAsString)
        Assertions.assertEquals(200, resp.status)
    }

    @Test
    fun createBookNotFoundTest() {
        val publisher = Publisher(1, "Castellana")
        entityManager.persist(publisher)
        entityManager.persist(Book("50","56516511",10,"Programacion_web", publisher))
        val body = """
           {
            "id": "50",
            "isbn_libro": "5454654",
            "cantidad": 10,
            "nombre_Libro": "Programacion web",
            "id_publisher":{
                "id": 1,
                "nombre_editorial": "Castellana"
            }
            }
        """.trimIndent()

        val request = MockMvcRequestBuilders
            .post("/books")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)

        val response = mocMvc.perform(request)
        val resp = response.andReturn().response
        //println(resp.contentAsString)
        Assertions.assertEquals(412, resp.status)
        Assertions.assertEquals("{\"message\":\"This libro already exists\",\"code\":412}".trimIndent(),
            resp.contentAsString)
    }

    @Test
    fun editBookHappyPathTest() {
        //prerequisitos..
        val publisher = Publisher(1, "Castellana")
        entityManager.persist(publisher)
        entityManager.persist(Book("1","16551651",10,"Prgramacion web",publisher))

        val body = """
           {
            "id": "1",
            "isbn_libro":"265416541",
            "cantidad": 20,
            "nombre_Libro": "lAS HISTORIAS DE ARTURITO",
            "id_publisher":{
                "id": 1,
                "nombre_editorial": "castellano"
            }
        }
        """.trimIndent()

        val request = MockMvcRequestBuilders
            .put("/books/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)

        val response = mocMvc.perform(request)
        val resp = response.andReturn().response
        //println(resp.contentAsString)
        Assertions.assertEquals(200, resp.status)
    }

    @Test
    fun entregarLibroHappyPathTest() {
        //prerequisitos..
        val publisher = Publisher(1, "Castellana")
        entityManager.persist(publisher)
        val book = Book("1","16551651",11,"Prgramacion web",publisher)
        entityManager.persist(book)
        val person = User("50","Rodriguez","Javier")
        entityManager.persist(person)
        val borrow = Borrow(30, Date(2021,10,10),person,book)
        entityManager.persist(borrow)
        val body = """
            {
            "id_borrow": 30,
            "id_user": "50",
            "id_Book": "1" 
            }
        """.trimIndent()

        val request = MockMvcRequestBuilders
            .delete("/books")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)

        val response = mocMvc.perform(request)
        val resp = response.andReturn().response
        //println(resp.contentAsString)
        Assertions.assertEquals(200, resp.status)
    }

    @Test
    fun getUsersHasBookHappyPathTest() {
        //prerequisitos..
        val publisher = Publisher(1, "Castellana")
        entityManager.persist(publisher)
        val book = Book("1","16551651",11,"Prgramacion web",publisher)
        entityManager.persist(book)
        val person = User("50","Rodriguez","Javier")
        entityManager.persist(person)
        val borrow = Borrow(30, Date(2021,10,10),person,book)
        entityManager.persist(borrow)

        val body = """
        """.trimIndent()

        val request = MockMvcRequestBuilders
            .get("/books/30/borrows")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)

        val response = mocMvc.perform(request)
        val resp = response.andReturn().response
        //println(resp.contentAsString)
        Assertions.assertEquals(200, resp.status)
    }


}