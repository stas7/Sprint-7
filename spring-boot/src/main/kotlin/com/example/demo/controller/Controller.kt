package com.example.demo.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class Controller {

    @GetMapping("ex1")
    fun ex1 () = "Упражнение SpringBoot"
}