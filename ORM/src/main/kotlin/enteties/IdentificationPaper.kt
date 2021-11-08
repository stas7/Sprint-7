package enteties

import javax.persistence.*

@Entity
class IdentificationPaper(
    @GeneratedValue
    @Id
    var id: Long = 0,

    @Enumerated(value = EnumType.STRING)
    var identificationPaper: IdentificationPaperType,

    @Column(name = "id_number", length = 64)
    var identificationPaperNumber: String
) {
    @ManyToOne
    @JoinColumn(name = "person_bio_id")
    var personBio: PersonBio? = null
}

enum class IdentificationPaperType {
    REGULAR_PASSPORT_RF,
    FOREIGN_CITIZEN_ID,
    REFUGEE_ID
}