package eam.edu.co.ingesoft.prestamoLibro.repository

import eam.edu.co.ingesoft.prestamoLibro.model.Libro
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@Component //anotacion que nos dice que esta es una clase manejada por springboot
@Transactional //para que las operaciones sobre la BD funcionen.
class LibroRepository {
    @Autowired //esta anotacion indica que springboot se encargara de instanciar esta clase.
    lateinit var em: EntityManager //clase que nos da JPA para manipular las entidades.


    fun create(libro: Libro) {
        em.persist(libro) //inserta en la tabla que define la entidad.
    }

    //? quiere decir q algo puede ser null
    fun find(id: String): Libro? {
        //se el envia la clase que quiero buscar y el valor de la llave primaria que quiero buscar.
        return em.find(Libro::class.java, id) //busca en la bd por llave primaria
    }

    fun listNamesBooks(libro: Libro): List<String>? {
        return listOf(libro.nombre_Libro)
    }

    fun update(libro: Libro) {
        em.merge(libro) //actualizar un registro sobre la BD
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

    fun findByEditorial(id: Long): List<Libro> {
        val query = em.createQuery("SELECT lib FROM Libro lib WHERE lib.id_editorial.id =: id")
        query.setParameter("id", id)

        return query.resultList as List<Libro>
    }


}