package eam.edu.co.ingesoft.prestamoLibro.service

import eam.edu.co.ingesoft.prestamoLibro.repository.LibroAutorRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.persistence.EntityManager

@Service
class Libro_autorService {
    @Autowired
    lateinit var libroAutorRepository: LibroAutorRepository

    @Autowired
    lateinit var entityManager: EntityManager

    fun listAutoryByAutorBook(id: Long) = libroAutorRepository.findByAutor(id)
}