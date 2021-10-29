package eam.edu.co.ingesoft.prestamoLibro.model.requests

data class BookRequest(
    val id_borrow : Long,
    val id_user : String,
    val id_Book : String,
)
