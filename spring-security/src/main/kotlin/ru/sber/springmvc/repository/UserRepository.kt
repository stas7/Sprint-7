package ru.sber.springmvc.repository

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import ru.sber.springmvc.entity.User

interface UserRepository : CrudRepository<User?, Long?> {

    @Query("SELECT u FROM User u WHERE u.username = :username")
    fun getUserByUsername(@Param("username") username: String?): User?
}