package eam.edu.co.ingesoft.prestamoLibro.model

import java.io.Serializable
import java.util.*
import javax.persistence.*

@Table(name="prestamo")
@Entity
data class Prestamo(
    @Id
    @Column(name="id")
    val id: Long,

    @Column(name="fecha_prestamo")
    val fecha_prestamo: Date,

    @ManyToOne
    @JoinColumn(name="id_usuario")
    var id_usuario:Usuario,

    @ManyToOne
    @JoinColumn(name="id_libro")
    var libro:Libro,
): Serializable
