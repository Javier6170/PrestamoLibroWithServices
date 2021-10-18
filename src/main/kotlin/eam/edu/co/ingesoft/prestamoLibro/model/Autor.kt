package eam.edu.co.ingesoft.prestamoLibro.model

import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Table(name="Autor")
@Entity
data class Autor(
    @Id
    @Column(name="codigo_autor")
    val id: Long,

    @Column(name="name")
    var name:String,

    @Column(name="lastname")
    var lastname:String,
): Serializable
