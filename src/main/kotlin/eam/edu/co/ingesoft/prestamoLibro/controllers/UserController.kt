package eam.edu.co.ingesoft.prestamoLibro.controllers

import eam.edu.co.ingesoft.prestamoLibro.model.entities.User
import eam.edu.co.ingesoft.prestamoLibro.service.BorrowService
import eam.edu.co.ingesoft.prestamoLibro.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*


/**
* crear usuario
*/
@RestController
@RequestMapping("/users")
class UserController {
    @Autowired
    lateinit var userService:UserService

    @Autowired
    lateinit var borrowService: BorrowService

    @PostMapping
    fun createUser(@RequestBody user: User) {
        userService.createUser(user)
    }

    //contacts/{{id}} ---> GET /contacts/1--> expresando es el contacto con id X
    //los contactos de una persona  GET /persons/{{id}}/contacts
    //todos los contactos  GET /persons/contacts (estaria esperando la info de las personas) , GET /contacts (solo la info de los contacts
    //agregar un contacto a una persona: POST  /persons/{{id}}/contacts

    @GetMapping("/{id}/borrows")
    fun getBorrowsByUser(@PathVariable("id") idUser: String) = borrowService.findByUser(idUser)
}