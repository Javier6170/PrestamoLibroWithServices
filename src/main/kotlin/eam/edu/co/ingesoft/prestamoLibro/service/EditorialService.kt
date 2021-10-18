package eam.edu.co.ingesoft.prestamoLibro.service

import eam.edu.co.ingesoft.prestamoLibro.exception.BusinessException
import eam.edu.co.ingesoft.prestamoLibro.model.Editorial
import eam.edu.co.ingesoft.prestamoLibro.repository.EditorialRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.persistence.EntityManager

@Service
class EditorialService {
    @Autowired
    lateinit var editorialRepository: EditorialRepository

    @Autowired
    lateinit var entityManager: EntityManager

    fun createEditorial(editorial: Editorial) {
        val editorialById = editorialRepository.find(editorial.id)

        if (editorialById != null) {
            throw BusinessException("This editorial already exists")
        }
        editorialRepository.create(editorial)
    }
}