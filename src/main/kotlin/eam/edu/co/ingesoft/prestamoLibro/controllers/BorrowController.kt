package eam.edu.co.ingesoft.prestamoLibro.controllers

import eam.edu.co.ingesoft.prestamoLibro.model.entities.Borrow
import eam.edu.co.ingesoft.prestamoLibro.service.BorrowService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/borrows")
class BorrowController {
    @Autowired
    lateinit var borrowService: BorrowService

    @PostMapping("/{idUsuario}/{idBook}")
    fun createBorrowBook(@PathVariable("idUsuario") idUsuario: String,@PathVariable("idBook") idBook: String,
                            @Validated @RequestBody borrow: Borrow){
        borrowService.prestarLibro(borrow,idUsuario, idBook)
    }
}