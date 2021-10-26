package eam.edu.co.ingesoft.prestamoLibro.model

import java.io.Serializable
import javax.persistence.*

@Table(name="libro")
@Entity
data class Libro(
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
    val id_editorial:Editorial,
): Serializable
