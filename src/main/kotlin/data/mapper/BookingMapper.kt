package data.mapper

import data.entity.BookingEntity
import domain.model.BookingDto
import domain.model.BookingWithUserAndRoomDto
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.statements.InsertStatement

fun InsertStatement<Number>.toBookingDto(): BookingDto {
    return BookingDto(
        id = this[BookingEntity.id].value,
        userId = this[BookingEntity.userId],
        roomId = this[BookingEntity.roomId],
        startTime = this[BookingEntity.startTime],
        endTime = this[BookingEntity.endTime],
        status = this[BookingEntity.status],
//        createdAt = this[BookingEntity.createdAt]
    )
}

fun ResultRow.toBookingDto(): BookingDto {
    return BookingDto(
        id = this[BookingEntity.id].value,
        userId = this[BookingEntity.userId],
        roomId = this[BookingEntity.roomId],
        startTime = this[BookingEntity.startTime],
        endTime = this[BookingEntity.endTime],
        status = this[BookingEntity.status],
//        createdAt = this[BookingEntity.createdAt]
    )
}

fun ResultRow.toBookingWithUserAndRoomDto(): BookingWithUserAndRoomDto {
    return BookingWithUserAndRoomDto(
        id = this[BookingEntity.id].value,
        user = this.toUserDto(),
        room = this.toRoomDto(),
        startTime = this[BookingEntity.startTime],
        endTime = this[BookingEntity.endTime],
        status = this[BookingEntity.status],
//        createdAt = this[BookingEntity.createdAt]
    )
}