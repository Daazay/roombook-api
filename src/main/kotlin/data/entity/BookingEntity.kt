package data.entity

import domain.model.BookingStatus
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentDateTime
import org.jetbrains.exposed.sql.kotlin.datetime.datetime
import java.util.UUID

object BookingEntity : UUIDTable("bookings") {
    val userId = uuid("user_id").references(UserEntity.id)
    val roomId = uuid("room_id").references(RoomEntity.id)
    val startTime = datetime("start_time")
    val endTime = datetime("end_time")
    val status = enumerationByName("status", 10, BookingStatus::class)
//    val createdAt = datetime("created_at").defaultExpression(CurrentDateTime)
}
