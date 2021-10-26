package eam.edu.co.ingesoft.prestamoLibro.service

import eam.edu.co.ingesoft.prestamoLibro.exception.BusinessException
import eam.edu.co.ingesoft.prestamoLibro.model.Libro
import eam.edu.co.ingesoft.prestamoLibro.repository.LibroRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.persistence.EntityManager
import javax.persistence.EntityNotFoundException

@Service
class LibroService {
    @Autowired
    lateinit var libroRepository: LibroRepository

    @Autowired
    lateinit var entityManager: EntityManager

    fun createlibro(libro: Libro) {
        val librorById = libroRepository.find(libro.id)
        val libroNames = libroRepository.listNamesBooks(libro)

        if (librorById != null && libroNames != null) {
            throw BusinessException("This libro already exists")
        }
        libroRepository.create(libro)
    }

    fun editLibro(libro: Libro){
        libroRepository.find(libro.id?:"") ?: throw EntityNotFoundException("This book does not exist")
        libroRepository.update(libro)
    }

    fun entregarLibro(libro: Libro) {
        libroRepository.find(libro.id)
            ?: throw BusinessException("This book does not exists")
        val libroFind = entityManager.find(Libro::class.java, libro.id)
        val cantidadAnterior = libroFind.cantidad
        val cantidadNueva = cantidadAnterior + 1
        libroFind.cantidad = cantidadNueva

        libroRepository.update(libroFind)
    }
}