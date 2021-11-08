package enteties

import org.hibernate.annotations.NaturalId
import javax.persistence.*

@Entity
class PersonBio (
    @Id
    @GeneratedValue
    var id: Long = 0,

    @NaturalId
    @Column(length = 127)
    var name: String,

    @Column(name = "first_name", length = 127)
    var firstName: String,

    @Column(name = "second_name", length = 127)
    var secondName: String,

    @Enumerated(value = EnumType.STRING)
    var clientCategory: ClientCategory,

    @OneToMany(mappedBy="personBio", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var identificationPaper: MutableList<IdentificationPaper>
//    ,
//
//    @OneToMany(mappedBy="personBio", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
//    var products: MutableList<Products>
        ) {
    override fun toString() : String {
        return "Клиент $name $firstName $secondName. Категория клиента $clientCategory"
    }
}

enum class ClientCategory {
    REGULAR,
    PREMIUM,
    VIP
}