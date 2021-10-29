package eam.edu.co.ingesoft.prestamoLibro.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import eam.edu.co.ingesoft.prestamoLibro.model.entities.Book
import eam.edu.co.ingesoft.prestamoLibro.model.entities.Publisher
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.transaction.annotation.Transactional
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
        entityManager.persist(Book("1","16551651",10,"Prgramacion web",publisher))

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
            "id": "50",
            "isbn_libro":"265416541",
            "cantidad": 20,
            "nombre_Libro": "lAS HISTORIAS DE ARTURITO",
            "id_publisher":{
                "id": 45,
                "nombre_editorial": "castellano"
            }
        }
        """.trimIndent()

        val request = MockMvcRequestBuilders
            .put("/books/50")
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
        entityManager.persist(Book("1","16551651",10,"Prgramacion web",publisher))

        val body = """
        """.trimIndent()

        val request = MockMvcRequestBuilders
            .delete("/books/50/50/30")
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
        entityManager.persist(Book("1","16551651",10,"Prgramacion web",publisher))

        val body = """
        """.trimIndent()

        val request = MockMvcRequestBuilders
            .get("/books/50/borrows")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)

        val response = mocMvc.perform(request)
        val resp = response.andReturn().response
        //println(resp.contentAsString)
        Assertions.assertEquals(200, resp.status)
    }


}