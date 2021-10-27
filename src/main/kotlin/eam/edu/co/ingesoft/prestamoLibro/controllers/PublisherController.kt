package eam.edu.co.ingesoft.prestamoLibro.controllers

import eam.edu.co.ingesoft.prestamoLibro.model.entities.Publisher
import eam.edu.co.ingesoft.prestamoLibro.service.PublisherService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
/**
 * todas las operaciones que se definan en este controlador empezaran por /person
 */
@RequestMapping("/editoriales")
class PublisherController {
    @Autowired
    lateinit var publisherService: PublisherService

    @PostMapping
    fun createEditorial(@RequestBody publisher: Publisher) {
        publisherService.createEditorial(publisher)
    }

}