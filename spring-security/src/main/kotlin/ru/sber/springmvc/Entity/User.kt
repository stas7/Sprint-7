package ru.sber.springmvc.entity

import javax.persistence.*

@Entity
@Table(name = "users")
class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Int? = null
    private val username: String? = null
    private val password: String? = null
    private val enabled: Int = 0

    @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    @JoinTable(
        name = "users_roles",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "role_id")]
    )
    private val roles: Set<Role> = HashSet()

    fun getRoles(): Set<Role> {
        return roles
    }
    fun getPassword(): String? {
        return password
    }

    fun getUsername(): String? {
        return username
    }

    fun isEnabled(): Boolean {
        return enabled == 1
    }
}