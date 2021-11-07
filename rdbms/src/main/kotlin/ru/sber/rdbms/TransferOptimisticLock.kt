package ru.sber.rdbms

import java.sql.Connection
import java.sql.DriverManager

class TransferOptimisticLock {

    private val connection: Connection = DriverManager.getConnection(
        "jdbc:postgresql://localhost:5432/db",
        "postgres",
        "postgres"
    )

    fun transfer(accountId1: Long, accountId2: Long, amount: Long) {
        val actualSourceVersion: Int
        val actualDestinationVersion: Int

        connection.use { connection ->
            val autoCommit = connection.autoCommit
            try {
                connection.autoCommit = false
                val getSourceAccInfoSt =
                    connection.prepareStatement("select * from account1 where id = $accountId1")
                getSourceAccInfoSt.use { st ->
                    st.executeQuery().use { resultSet ->
                        resultSet.next()
                        if (resultSet.getInt(2) - amount < 0) {
                            throw OutOfMoneyException("Actual balance ${resultSet.getInt(2)}, less then transferef amount $amount")
                        }
                        actualSourceVersion = resultSet.getInt(3)
                    }
                }
                val decreaseFundOnSourceAccSt =
                    connection.prepareStatement("update account1 set amount = amount - $amount, version = version + 1 where id = $accountId1 and version = $actualSourceVersion")
                var result = decreaseFundOnSourceAccSt.executeUpdate()
                if (result == 0) {
                    throw LockOnBaseException("Balance was already changed")
                }


                val getDestinationAccSt =
                    connection.prepareStatement("select * from account1 where id = $accountId2")
                getDestinationAccSt.executeQuery().use { resultSet ->
                    resultSet.next()
                    actualDestinationVersion = resultSet.getInt("version")
                }
                val increaseFundOnDestinationAccSt =
                    connection.prepareStatement(
                        "update account1 set amount = amount + $amount, version = version + 1 " +
                                "where id = $accountId2 and version = $actualDestinationVersion"
                    )
                result = increaseFundOnDestinationAccSt.executeUpdate()
                if (result == 0) {
                    throw LockOnBaseException("Balance was already changed")
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