package eam.edu.co.ingesoft.prestamoLibro.model.entities

import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Table(name="editorial")
@Entity
data class Publisher(
    @Id
    @Column(name="codigo_editorial")
    val id: Long,

    @Column(name="nombre_editorial")
    var nombre_editorial:String,
): Serializable
