package eam.edu.co.ingesoft.prestamoLibro.service

import eam.edu.co.ingesoft.prestamoLibro.exception.BusinessException
import eam.edu.co.ingesoft.prestamoLibro.model.Usuario
import eam.edu.co.ingesoft.prestamoLibro.repository.UsuarioRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.persistence.EntityManager

@Service
class UsuarioService {
    @Autowired
    lateinit var usuarioRepository: UsuarioRepository

    @Autowired
    lateinit var entityManager: EntityManager

    fun createUser(usuario: Usuario) {
        val userById = usuarioRepository.find(usuario.user_identification)

        if (userById != null) {
            throw BusinessException("This person already exists")
        }
        usuarioRepository.create(usuario)
    }
}