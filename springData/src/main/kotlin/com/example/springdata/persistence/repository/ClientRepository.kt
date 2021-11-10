package com.example.springdata.persistence.repository

import com.example.springdata.persistence.entity.PersonBio
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface ClientRepository : CrudRepository<PersonBio, Long> {
    fun findByLastName(lastName: String): PersonBio

    @Query("SELECT p FROM PersonBio p  WHERE p.firstName = :fname")
    fun findByFirstName(@Param("fname") firstName: String): List<PersonBio>
}