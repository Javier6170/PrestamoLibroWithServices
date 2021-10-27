package eam.edu.co.ingesoft.prestamoLibro.controllers

import eam.edu.co.ingesoft.prestamoLibro.model.entities.User
import eam.edu.co.ingesoft.prestamoLibro.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*


/**
* crear usuario
*/
@RestController
@RequestMapping("/usuarios")
class UserController {
    @Autowired
    lateinit var userService:UserService

    @PostMapping
    fun createUser(@RequestBody user: User) {
        userService.createUser(user)
    }
}