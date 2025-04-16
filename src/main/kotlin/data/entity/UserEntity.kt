package data.entity

import domain.model.UserRole
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentDateTime
import org.jetbrains.exposed.sql.kotlin.datetime.datetime
import java.util.UUID

object UserEntity: UUIDTable("users") {
    val username = varchar("username", 64).uniqueIndex()
    val email = varchar("email", 128)
    val role = enumeration("role", UserRole::class).default(UserRole.USER)
    val password = varchar("password", 64)
    val salt = varchar("salt", 64)
//    val createdAt = datetime("created_at").defaultExpression(CurrentDateTime)

    init {
        index(true, id)
    }
}
