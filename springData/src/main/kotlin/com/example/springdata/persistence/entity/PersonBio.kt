package com.example.springdata.persistence.entity

import org.hibernate.annotations.NaturalId
import javax.persistence.*

@Entity
@Table(name = "personbio")
class PersonBio (
    @Id
    @GeneratedValue
    var id: Long = 0,

    @NaturalId
    @Column(name = "last_name", length = 127)
    var lastName: String,

    @Column(name = "first_name", length = 127)
    var firstName: String,

    @Column(name = "second_name", length = 127)
    var secondName: String,

    @Column(name = "clientcategory", length = 127)
    @Enumerated(value = EnumType.STRING)
    var clientCategory: ClientCategory

) {
    override fun toString() : String {
        return "Клиент $lastName $firstName $secondName. Категория клиента $clientCategory"
    }
}

enum class ClientCategory {
    REGULAR,
    PREMIUM,
    VIP
}