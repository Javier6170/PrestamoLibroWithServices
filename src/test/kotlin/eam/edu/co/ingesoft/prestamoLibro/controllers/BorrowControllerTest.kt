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
class BorrowControllerTest {
    //clase que simula el servidor web que expone las operaciones de los
    @Autowired
    lateinit var entityManager: EntityManager

    @Autowired
    lateinit var mocMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Test
    fun createBorrowHappyPathTest() {
        //prerequisitos..
        val publisher = Publisher(1, "Castellana")
        entityManager.persist(publisher)
        val book = Book("1","16551651",11,"Prgramacion web",publisher)
        entityManager.persist(book)
        val person = User("50","Rodriguez","Javier")
        entityManager.persist(person)
        val body = """
           {
            "id": 30,
            "fecha_prestamo":"2021-10-28",
            "id_user":{
                "user_identification": "50",
                "apellido_usuario": "Rodriguez",
                "nombre_usuario": "Javier"
            },
            "book":{
                "id": "1",
                "isbn_libro":"1654654",
                "cantidad": 50,
                "nombre_Libro": "Programacion del futuro",
                "id_publisher":{
                    "id": 1,
                    "nombre_editorial": "castellano"
                }
            }
        }
        """.trimIndent()

        val request = MockMvcRequestBuilders
            .post("/borrows/50/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)

        val response = mocMvc.perform(request)
        val resp = response.andReturn().response
        //println(resp.contentAsString)
        Assertions.assertEquals(200, resp.status)
    }


}