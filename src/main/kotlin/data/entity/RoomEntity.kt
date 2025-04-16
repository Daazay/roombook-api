package data.entity

import domain.model.RoomStatus
import domain.model.RoomType
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentDateTime
import org.jetbrains.exposed.sql.kotlin.datetime.datetime

object RoomEntity : UUIDTable("rooms") {
    val buildingId = uuid("building_id").references(BuildingEntity.id)
    val name = varchar("name", 255)
    val type = enumerationByName("type", 10, RoomType::class)
    val capacity = integer("capacity")
    val status = enumerationByName("status", 15, RoomStatus::class)
//    val createdAt = datetime("created_at").defaultExpression(CurrentDateTime)
}
