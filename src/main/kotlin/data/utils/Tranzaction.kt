package data.utils

import org.jetbrains.exposed.sql.transactions.transaction

fun<T> tranzaction(stmt: () -> T): T = transaction {
    try {
        return@transaction stmt()
    } catch (e: Exception) {
        rollback()
        throw e
    }
}