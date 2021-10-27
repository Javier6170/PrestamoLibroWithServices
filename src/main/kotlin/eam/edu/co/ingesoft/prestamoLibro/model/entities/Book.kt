package eam.edu.co.ingesoft.prestamoLibro.model.entities

import java.io.Serializable
import javax.persistence.*

@Table(name="libro")
@Entity
data class Book(
    @Id
    @Column(name="codigo_libro")
    var id:String,

    @Column(name="isbn_libro")
    var isbn_libro:String,

    @Column(name="stock")
    var cantidad:Int,

    @Column(name="nombre_Libro")
    var nombre_Libro:String,

    @ManyToOne
    @JoinColumn(name="id_editorial")
    var id_publisher: Publisher
): Serializable
