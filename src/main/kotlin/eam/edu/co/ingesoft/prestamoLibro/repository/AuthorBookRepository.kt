package eam.edu.co.ingesoft.prestamoLibro.repository

import eam.edu.co.ingesoft.prestamoLibro.model.entities.AuthorBook
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@Component //anotacion que nos dice que esta es una clase manejada por springboot
@Transactional //para que las operaciones sobre la BD funcionen.
class AuthorBookRepository {
    @Autowired //esta anotacion indica que springboot se encargara de instanciar esta clase.
    lateinit var em: EntityManager //clase que nos da JPA para manipular las entidades.


    fun create(libroAutor: AuthorBook){
        em.persist(libroAutor) //inserta en la tabla que define la entidad.
    }

    //? quiere decir q algo puede ser null
    fun find(id:Long): AuthorBook?{
        //se el envia la clase que quiero buscar y el valor de la llave primaria que quiero buscar.
        return em.find(AuthorBook::class.java, id) //busca en la bd por llave primaria
    }

    fun update(libroAutor: AuthorBook) {
        em.merge(libroAutor) //actualizar un registro sobre la BD
    }

    fun delete(id: Long) {
        //buscan por id la entidad que quiero borrar
        val libroAutor = find(id)

        //solo puedo borrar una persona que exista...
        if (libroAutor!=null) {
            //borra la entidad de la BD, recibe por parametro la entidad a borrrar
            em.remove(libroAutor)
        }
    }

    fun findByAutor(id: Long): List<AuthorBook> {
        val query = em.createQuery("SELECT libAutor FROM AuthorBook libAutor WHERE libAutor.author.id =: id")
        query.setParameter("id", id)

        return query.resultList as List<AuthorBook>
    }

    fun findByLibro(id: String): List<AuthorBook> {
        val query = em.createQuery("SELECT libAutor FROM AuthorBook libAutor WHERE libAutor.book.id =: id")
        query.setParameter("id", id)

        return query.resultList as List<AuthorBook>
    }

}