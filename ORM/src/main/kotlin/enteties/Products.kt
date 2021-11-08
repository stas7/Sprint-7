package enteties

import javax.persistence.*

@Entity
class Products (
    @GeneratedValue
    @Id
    var id: Long = 0,

    @Enumerated(value = EnumType.STRING)
    var productType: ProductType,
    var funds: Long
) {
    @ManyToOne
    @JoinColumn(name = "person_bio_id")
    var personBio: PersonBio? = null
}

enum class ProductType {
    ACCOUNT,
    CREDIT,
    CARD
}
