import enteties.*
import org.hibernate.SessionFactory
import org.hibernate.cfg.Configuration

fun main() {
    val sessionFactory = Configuration().configure()
        .addAnnotatedClass(PersonBio::class.java)
        .addAnnotatedClass(ProductType::class.java)
        .addAnnotatedClass(IdentificationPaper::class.java)
        .buildSessionFactory()

    sessionFactory.use { sessionFactory ->
        val dao = PersonDAO(sessionFactory)

        val clientA = PersonBio(
            name = "Petrov100",
            firstName = "Petr",
            secondName = "Petrovich1",
            clientCategory = ClientCategory.PREMIUM,
            identificationPaper = mutableListOf(
                IdentificationPaper(
                    identificationPaper = IdentificationPaperType.REGULAR_PASSPORT_RF,
                    identificationPaperNumber = "666777"
                )
            )
//            ,
//            products = mutableListOf(
//                Products(productType = ProductType.ACCOUNT, funds = 4536),
//                Products(productType = ProductType.CARD, funds = 789)
//            )
        )

        val clientB = PersonBio(
            name = "Ivanov100",
            firstName = "Ivan",
            secondName = "Ivanovich1",
            clientCategory = ClientCategory.PREMIUM,
            identificationPaper = mutableListOf(
                IdentificationPaper(
                    identificationPaper = IdentificationPaperType.REGULAR_PASSPORT_RF,
                    identificationPaperNumber = "999888"
                )
            )
//            ,
//            products = mutableListOf(
//                Products(productType = ProductType.CREDIT, funds = 123)
//            )
        )



        dao.save(clientA)

        dao.save(clientB)

        var found = dao.find(clientA.id)
        println("$found \n")

        found = dao.find(clientB.name)
        println("$found \n")
    }
}


class PersonDAO(
    private val sessionFactory: SessionFactory
) {
    fun save(personBio: PersonBio) {
        sessionFactory.openSession().use { session ->
            session.beginTransaction()
            session.save(personBio)
            session.transaction.commit()
        }
    }

    fun find(id: Long): PersonBio? {
        val result: PersonBio?
        sessionFactory.openSession().use { session ->
            session.beginTransaction()
            result = session.get(PersonBio::class.java, id)
            session.transaction.commit()
        }
        return result
    }

    fun find(lastName: String): PersonBio? {
        val result: PersonBio?
        sessionFactory.openSession().use { session ->
            session.beginTransaction()
            result =
                session.byNaturalId(PersonBio::class.java).using("name", lastName).loadOptional().orElse(null)
            session.transaction.commit()
        }
        return result
    }

    fun findAll(): List<PersonBio> {
        val result: List<PersonBio>
        sessionFactory.openSession().use { session ->
            session.beginTransaction()
            result = session.createQuery("from personBio").list() as List<PersonBio>
            session.transaction.commit()
        }
        return result
    }
}