package eam.edu.co.ingesoft.prestamoLibro.service

import eam.edu.co.ingesoft.prestamoLibro.exception.BusinessException
import eam.edu.co.ingesoft.prestamoLibro.model.Autor
import eam.edu.co.ingesoft.prestamoLibro.repository.AutorRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.persistence.EntityManager

@Service
class AutorService {
    @Autowired
    lateinit var autorRepository: AutorRepository

    @Autowired
    lateinit var entityManager: EntityManager

    fun createAutor(autor: Autor) {
        val autorById = autorRepository.find(autor.id)

        if (autorById != null) {
            throw BusinessException("This autor already exists")
        }
        autorRepository.create(autor)
    }
}