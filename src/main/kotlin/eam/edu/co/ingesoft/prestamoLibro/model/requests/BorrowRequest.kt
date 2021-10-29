package eam.edu.co.ingesoft.prestamoLibro.model.requests

data class BorrowRequest(
    val id : Long,
    val id_user : String?,
    val id_Book : String?,
)
