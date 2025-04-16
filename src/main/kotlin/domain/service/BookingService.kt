package domain.service

import data.entity.UserEntity
import domain.model.BookingCreateDto
import domain.model.BookingDto
import domain.model.BookingUpdateDto
import domain.repository.BookingRepository
import java.util.UUID

class BookingService(
    private val bookingRepository: BookingRepository,
) {
    suspend fun create(booking: BookingCreateDto): BookingDto {
        return bookingRepository.insert(booking)
    }

    suspend fun getById(id: UUID): BookingDto {
        return bookingRepository.findById(id)
            ?: throw IllegalArgumentException("Booking not found.")
    }

    suspend fun getAll(): List<BookingDto> {
        return bookingRepository.findAll()
    }

    suspend fun update(id: UUID, booking: BookingUpdateDto): Boolean {
        return bookingRepository.update(id, booking)
    }

    suspend fun deleteById(id: UUID): Boolean {
        return bookingRepository.deleteById(id)
    }
}
