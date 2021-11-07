package ru.sber.rdbms

import java.sql.Connection
import java.sql.DriverManager
import kotlin.math.max
import kotlin.math.min

class TransferPessimisticLock {
    private val connection: Connection = DriverManager.getConnection(
        "jdbc:postgresql://localhost:5432/postgres",
        "postgres",
        "postgres"
    )

    fun transfer(accountId1: Long, accountId2: Long, amount: Long) {
        val minAccIdFunds: Long
        val maxAccIdFunds: Long

        connection.use { connection ->
            val autoCommit = connection.autoCommit
            try {
                connection.autoCommit = false
                val blockMinIdAccSt = connection.prepareStatement(
                    "select * from account1 where id = ${
                        min(
                            accountId1,
                            accountId2
                        )
                    } for update "
                )
                blockMinIdAccSt.executeQuery().use { resultSet ->
                    resultSet.next()
                    minAccIdFunds = resultSet.getLong(2)
                }
                val blockMaxIdAccSt = connection.prepareStatement(
                    "select * from account1 where id = ${
                        max(
                            accountId1,
                            accountId2
                        )
                    } for update "
                )
                blockMaxIdAccSt.executeQuery().use { resultSet ->
                    resultSet.next()
                    maxAccIdFunds = resultSet.getLong(2)

                }

                //Эту ересь сделал чтоб не делать лишнего запроса к базе, т.к. это наиболее тяжелая операция
                if (accountId1 < accountId2 && minAccIdFunds < amount) {
                    throw OutOfMoneyException("Actual balance $minAccIdFunds, less then transfer amount $amount")
                }
                if (accountId1 > accountId2 && maxAccIdFunds < amount) {
                    throw OutOfMoneyException("Actual balance $maxAccIdFunds, less then transfer amount $amount")
                }


                val decreaseFundOnSourceAccSt =
                    connection.prepareStatement("update account1 set amount = amount - $amount where id = $accountId1")
                decreaseFundOnSourceAccSt.use { statement ->
                    statement.executeUpdate()
                }

                val increaseFundOnDestinationAccSt =
                    connection.prepareStatement("update account1 set amount = amount + $amount where id = $accountId2")
                increaseFundOnDestinationAccSt.use { statement ->
                    statement.executeUpdate()
                }
                connection.commit()
            } catch (e: Exception) {
                println(e.message)
                connection.rollback()
            } finally {
                connection.autoCommit = autoCommit
            }
        }

    }
}