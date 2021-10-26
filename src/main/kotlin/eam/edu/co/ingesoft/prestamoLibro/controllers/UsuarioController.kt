package eam.edu.co.ingesoft.prestamoLibro.controllers

import eam.edu.co.ingesoft.prestamoLibro.model.Libro
import eam.edu.co.ingesoft.prestamoLibro.model.Usuario
import eam.edu.co.ingesoft.prestamoLibro.service.UsuarioService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*


/**
* crear usuario
*/
@RestController
@RequestMapping("/usuarios")
class UsuarioController {
    @Autowired
    lateinit var usuarioService:UsuarioService

    @PostMapping
    fun createUser(@RequestBody usuario: Usuario) {
        usuarioService.createUser(usuario)
    }
}