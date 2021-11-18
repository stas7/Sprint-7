package ru.sber.springmvc.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import ru.sber.springmvc.repository.UserRepository

class AdBookUserServiceImpl : UserDetailsService {
    @Autowired
    private val userRepository: UserRepository? = null

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository!!.getUserByUsername(username)
            ?: throw UsernameNotFoundException("Not Found: $username")
        return AdBookUserDetails(user)
    }
}