package com.example.springdata

import com.example.springdata.persistence.entity.ClientCategory
import com.example.springdata.persistence.entity.PersonBio
import com.example.springdata.persistence.repository.ClientRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringDataApplication(
    private val clientRepository: ClientRepository
) : CommandLineRunner {
    override fun run(vararg args: String?) {
        val clientA = PersonBio(
            lastName = "Petrov102",
            firstName = "Petr",
            secondName = "Petrovich1",
            clientCategory = ClientCategory.PREMIUM
        )

        val clientB = PersonBio(
            lastName = "Ivanov102",
            firstName = "Ivan",
            secondName = "Ivanovich1",
            clientCategory = ClientCategory.REGULAR
        )

        clientRepository.saveAll(listOf(clientA, clientB))

        println("первый поиск\n")
        println("поиск по имени ${clientRepository.findByLastName("Petrov101")}")
        println("второй поиск\n")
        println(clientRepository.findByFirstName("Ivan"))
    }
}

fun main(args: Array<String>) {
    runApplication<SpringDataApplication>(*args)
}
