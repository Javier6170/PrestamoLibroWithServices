package eam.edu.co.ingesoft.prestamoLibro.service

import eam.edu.co.ingesoft.prestamoLibro.exception.BusinessException
import eam.edu.co.ingesoft.prestamoLibro.model.Prestamo
import eam.edu.co.ingesoft.prestamoLibro.repository.LibroRepository
import eam.edu.co.ingesoft.prestamoLibro.repository.PrestamoRepositry
import eam.edu.co.ingesoft.prestamoLibro.repository.UsuarioRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.persistence.EntityManager

@Service
class PrestamoService {
    @Autowired
    lateinit var prestamoRepository: PrestamoRepositry

    @Autowired
    lateinit var usuarioRepository: UsuarioRepository

    @Autowired
    lateinit var entityManager: EntityManager

    @Autowired
    lateinit var libroRepository: LibroRepository

    fun listaPrestamoDeUnUsuario(id: String): List<Prestamo> {
        val usuario = usuarioRepository.find(id)
            ?: throw BusinessException("The user does not exist")
        return prestamoRepository.findByUsuario(id)
    }

    fun prestarLibro(prestamo: Prestamo, usuarioId: String, libroId: String) {
        val usuario = usuarioRepository.find(usuarioId)
            ?: throw BusinessException("The user does not exist")

        val libro = libroRepository.find(libroId) ?: throw BusinessException("The book does not exist")
        val cantidadLibro = libro.cantidad


        if (cantidadLibro <= 0) {
            throw BusinessException("No hay ejemplares de este libro para prestar")
        }

        val prestamos = prestamoRepository.findByUsuario(usuarioId)

        if (prestamos.size > 5) {
            throw BusinessException("Solo 5 prestamos por persona")
        }

        val cantidadNueva = cantidadLibro - 1
        libro.cantidad = cantidadNueva

        prestamo.id_usuario=usuario
        prestamo.libro = libro
        prestamoRepository.create(prestamo)

    }
}