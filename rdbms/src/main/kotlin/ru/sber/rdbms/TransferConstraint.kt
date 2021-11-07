package ru.sber.rdbms

import java.sql.Connection
import java.sql.DriverManager

class TransferConstraint {

    private val connection: Connection = DriverManager.getConnection(
        "jdbc:postgresql://localhost:5432/postgres",
        "postgres",
        "postgres"
    )

    fun transfer(accountId1: Long, accountId2: Long, amount: Long) {
        val autoCommit = connection.autoCommit
        connection.autoCommit = false

        connection.use { connection ->
            try {
                val decreaseAmount =
                    connection.prepareStatement("update account1 set amount = amount - $amount where id = $accountId1")
                decreaseAmount.use { preparedStatement ->
                    preparedStatement.executeUpdate()
                }
                val increaseAmount =
                    connection.prepareStatement("update account1 set amount = amount + $amount where id = $accountId2")
                increaseAmount.use { preparedStatement ->
                    preparedStatement.executeUpdate()
                }
            } catch (e: Exception) {
                println(e.message)
                connection.rollback()
            } finally {
                connection.autoCommit = autoCommit
            }
        }
    }
}