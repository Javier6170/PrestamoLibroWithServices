package eam.edu.co.ingesoft.prestamoLibro.service

import eam.edu.co.ingesoft.prestamoLibro.exception.BusinessException
import eam.edu.co.ingesoft.prestamoLibro.model.entities.Borrow
import eam.edu.co.ingesoft.prestamoLibro.repository.BookRepository
import eam.edu.co.ingesoft.prestamoLibro.repository.BorrowRepository
import eam.edu.co.ingesoft.prestamoLibro.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.persistence.EntityManager

@Service
class BorrowService {
    @Autowired
    lateinit var prestamoRepository: BorrowRepository

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var entityManager: EntityManager

    @Autowired
    lateinit var bookRepository: BookRepository

    fun listaPrestamoDeUnUsuario(id: String): List<Borrow> {
        val usuario = userRepository.find(id)
            ?: throw BusinessException("The user does not exist")
        return prestamoRepository.findByUsuario(id)
    }

    fun prestarLibro(borrow: Borrow, usuarioId: String, libroId: String) {
        val usuario = userRepository.find(usuarioId)
            ?: throw BusinessException("The user does not exist")

        val libro = bookRepository.find(libroId) ?: throw BusinessException("The book does not exist")
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

        borrow.id_user=usuario
        borrow.book = libro
        prestamoRepository.create(borrow)

    }
}