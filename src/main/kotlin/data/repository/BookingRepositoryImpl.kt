package data.repository

import data.entity.BookingEntity
import data.entity.RoomEntity
import data.entity.UserEntity
import data.mapper.toBookingDto
import domain.model.BookingDto
import domain.model.BookingStatus
import domain.repository.BookingRepository
import data.utils.tranzaction
import domain.model.BookingCreateDto
import domain.model.BookingUpdateDto
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import java.util.UUID
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toKotlinLocalDateTime

class BookingRepositoryImpl : BookingRepository {
    init {
        tranzaction {
            SchemaUtils.create(BookingEntity)
        }
    }

    override suspend fun insert(booking: BookingCreateDto): BookingDto = tranzaction {
        if (UserEntity.selectAll().where { UserEntity.id eq booking.userId }.empty()) {
            throw IllegalArgumentException("User not found.")
        }
        if (RoomEntity.selectAll().where { RoomEntity.id eq booking.roomId }.empty()) {
            throw IllegalArgumentException("Room not found.")
        }

        BookingEntity.insert {
            it[userId] = booking.userId
            it[roomId] = booking.roomId
            it[startTime] = booking.startTime
            it[endTime] = booking.endTime
            it[status] = BookingStatus.PENDING
        }.toBookingDto()
    }

    override suspend fun findById(id: UUID): BookingDto? = tranzaction {
        BookingEntity.selectAll()
            .where { BookingEntity.id eq id }
            .singleOrNull()
            ?.toBookingDto()
    }

    override suspend fun findAll(): List<BookingDto> = tranzaction {
        BookingEntity.selectAll()
            .map { it.toBookingDto() }
    }

    override suspend fun update(id: UUID, booking: BookingUpdateDto): Boolean = tranzaction {
        if (booking.roomId != null && RoomEntity.selectAll().where { RoomEntity.id eq booking.roomId }.empty()) {
            throw IllegalArgumentException("Room not found.")
        }

        val count = BookingEntity.update({ BookingEntity.id eq id }) {
            booking.roomId?.let { roomId -> it[BookingEntity.roomId] = roomId }
            booking.startTime?.let { startTime -> it[BookingEntity.startTime] = startTime }
            booking.endTime?.let { endTime -> it[BookingEntity.endTime] = endTime }
            booking.status?.let { status -> it[BookingEntity.status] = status }
        }
        if (count == 0) {
            throw IllegalArgumentException("User not found")
        }
        true
    }

    override suspend fun deleteById(id: UUID): Boolean = tranzaction {
        val count = BookingEntity.deleteWhere { BookingEntity.id eq id }
        count > 0
    }
}
