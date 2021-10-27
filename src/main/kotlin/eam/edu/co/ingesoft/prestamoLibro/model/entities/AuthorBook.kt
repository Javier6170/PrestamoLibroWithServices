package eam.edu.co.ingesoft.prestamoLibro.model.entities

import java.io.Serializable
import javax.persistence.*

@Table(name="libro_autor")
@Entity
data class AuthorBook(
    @Id
    @Column(name="id")
    val id: Long,

    @ManyToOne
    @JoinColumn(name="id_autor")
    val author: Author,

    @ManyToOne
    @JoinColumn(name="id_libro")
    val book: Book,
): Serializable
