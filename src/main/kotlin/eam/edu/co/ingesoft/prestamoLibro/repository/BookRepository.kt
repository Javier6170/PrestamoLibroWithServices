package eam.edu.co.ingesoft.prestamoLibro.repository

import eam.edu.co.ingesoft.prestamoLibro.model.entities.Book
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@Component //anotacion que nos dice que esta es una clase manejada por springboot
@Transactional //para que las operaciones sobre la BD funcionen.
class BookRepository {
    @Autowired //esta anotacion indica que springboot se encargara de instanciar esta clase.
    lateinit var em: EntityManager //clase que nos da JPA para manipular las entidades.


    fun create(book: Book) {
        em.persist(book) //inserta en la tabla que define la entidad.
    }

    //? quiere decir q algo puede ser null
    fun find(id: String): Book? {
        //se el envia la clase que quiero buscar y el valor de la llave primaria que quiero buscar.
        return em.find(Book::class.java, id) //busca en la bd por llave primaria
    }

    fun listNamesBooks(book: Book): List<String>? {
        return listOf(book.nombre_Libro)
    }

    fun update(book: Book) {
        em.merge(book) //actualizar un registro sobre la BD
    }

    fun delete(id: String) {
        //buscan por id la entidad que quiero borrar
        val libro = find(id)

        //solo puedo borrar una persona que exista...
        if (libro != null) {
            //borra la entidad de la BD, recibe por parametro la entidad a borrrar
            em.remove(libro)
        }
    }

    fun findByEditorial(id: Long): List<Book> {
        val query = em.createQuery("SELECT lib FROM Book lib WHERE lib.id_publisher.id =: id")
        query.setParameter("id", id)

        return query.resultList as List<Book>
    }


}