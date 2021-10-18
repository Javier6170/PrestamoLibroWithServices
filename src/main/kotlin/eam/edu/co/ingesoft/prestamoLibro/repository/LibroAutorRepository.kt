package eam.edu.co.ingesoft.prestamoLibro.repository

import eam.edu.co.ingesoft.prestamoLibro.model.Libro_autor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@Component //anotacion que nos dice que esta es una clase manejada por springboot
@Transactional //para que las operaciones sobre la BD funcionen.
class LibroAutorRepository {
    @Autowired //esta anotacion indica que springboot se encargara de instanciar esta clase.
    lateinit var em: EntityManager //clase que nos da JPA para manipular las entidades.


    fun create(libroAutor: Libro_autor){
        em.persist(libroAutor) //inserta en la tabla que define la entidad.
    }

    //? quiere decir q algo puede ser null
    fun find(id:Long): Libro_autor?{
        //se el envia la clase que quiero buscar y el valor de la llave primaria que quiero buscar.
        return em.find(Libro_autor::class.java, id) //busca en la bd por llave primaria
    }

    fun update(libroAutor: Libro_autor) {
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

    fun findByAutor(id: Long): List<Libro_autor> {
        val query = em.createQuery("SELECT libAutor FROM Libro_autor libAutor WHERE libAutor.autor.id =: id")
        query.setParameter("id", id)

        return query.resultList as List<Libro_autor>
    }

    fun findByLibro(id: String): List<Libro_autor> {
        val query = em.createQuery("SELECT libAutor FROM Libro_autor libAutor WHERE libAutor.libro.id =: id")
        query.setParameter("id", id)

        return query.resultList as List<Libro_autor>
    }

}