package eam.edu.co.ingesoft.prestamoLibro.model

import java.io.Serializable
import javax.persistence.*

@Table(name="libro_autor")
@Entity
data class Libro_autor(
    @Id
    @Column(name="id")
    val id: Long,

    @ManyToOne
    @JoinColumn(name="id_autor")
    val autor:Autor,

    @ManyToOne
    @JoinColumn(name="id_libro")
    val libro:Libro,
): Serializable
