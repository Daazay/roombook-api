package domain.repository

import domain.model.BookingCreateDto
import domain.model.BookingDto
import domain.model.BookingUpdateDto
import java.util.UUID

interface BookingRepository {
    suspend fun insert(booking: BookingCreateDto): BookingDto
    suspend fun findById(id: UUID): BookingDto?
    suspend fun findAll(): List<BookingDto>
    suspend fun update(id: UUID, booking: BookingUpdateDto): Boolean
    suspend fun deleteById(id: UUID): Boolean
}
