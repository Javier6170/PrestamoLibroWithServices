package eam.edu.co.ingesoft.prestamoLibro.service

import eam.edu.co.ingesoft.prestamoLibro.repository.AuthorBookRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.persistence.EntityManager

@Service
class AuthorBookService() {
    @Autowired
    lateinit var authorBookRepository: AuthorBookRepository

    @Autowired
    lateinit var entityManager: EntityManager

    fun listAutoryByAutorBook(id: Long) = authorBookRepository.findByAutor(id)
}