package eam.edu.co.ingesoft.prestamoLibro.service

import eam.edu.co.ingesoft.prestamoLibro.exception.BusinessException
import eam.edu.co.ingesoft.prestamoLibro.model.entities.Publisher
import eam.edu.co.ingesoft.prestamoLibro.repository.PublisherRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.persistence.EntityManager

@Service
class PublisherService {
    @Autowired
    lateinit var publisherRepository: PublisherRepository

    @Autowired
    lateinit var entityManager: EntityManager

    fun createEditorial(publisher: Publisher) {
        val editorialById = publisherRepository.find(publisher.id)

        if (editorialById != null) {
            throw BusinessException("This editorial already exists")
        }
        publisherRepository.create(publisher)
    }
}