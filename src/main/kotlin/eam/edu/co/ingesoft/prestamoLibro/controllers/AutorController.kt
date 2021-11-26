package eam.edu.co.ingesoft.prestamoLibro.controllers

import eam.edu.co.ingesoft.prestamoLibro.model.entities.Author
import eam.edu.co.ingesoft.prestamoLibro.model.entities.User
import eam.edu.co.ingesoft.prestamoLibro.service.AuthorService
import eam.edu.co.ingesoft.prestamoLibro.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/autores")
class AutorController {

    @Autowired
    lateinit var authorService: AuthorService

    @PostMapping
    fun createUser(@RequestBody author: Author) {
        authorService.createAutor(author)
    }
}