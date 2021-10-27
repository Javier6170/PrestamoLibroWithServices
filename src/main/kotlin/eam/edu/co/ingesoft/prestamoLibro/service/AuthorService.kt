package eam.edu.co.ingesoft.prestamoLibro.service

import eam.edu.co.ingesoft.prestamoLibro.exception.BusinessException
import eam.edu.co.ingesoft.prestamoLibro.model.entities.Author
import eam.edu.co.ingesoft.prestamoLibro.repository.AuthorRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.persistence.EntityManager

@Service
class AuthorService {
    @Autowired
    lateinit var authorRepository: AuthorRepository

    @Autowired
    lateinit var entityManager: EntityManager

    fun createAutor(author: Author) {
        val autorById = authorRepository.find(author.id)

        if (autorById != null) {
            throw BusinessException("This autor already exists")
        }
        authorRepository.create(author)
    }
}