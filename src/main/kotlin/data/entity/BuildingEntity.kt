package data.entity

import data.entity.BookingEntity.defaultExpression
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentDateTime
import org.jetbrains.exposed.sql.kotlin.datetime.datetime
import java.util.UUID

object BuildingEntity : UUIDTable("buildings") {
    val name = varchar("name", 255)
    val address = varchar("address", 255)
    val createdAt = datetime("created_at").defaultExpression(CurrentDateTime)
}
