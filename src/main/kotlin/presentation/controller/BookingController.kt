package presentation.controller

import core.utils.Result
import domain.model.BookingCreateDto
import domain.model.BookingDto
import domain.model.BookingStatus
import domain.model.BookingUpdateDto
import domain.service.BookingService
import kotlinx.datetime.LocalDateTime
import org.jetbrains.exposed.sql.resolveColumnType
import org.jetbrains.exposed.sql.stringLiteral
import presentation.dto.request.BookingCreateRequest
import presentation.dto.request.BookingUpdateRequest
import presentation.dto.response.BookingResponse
import presentation.dto.response.BuildingResponse
import presentation.dto.response.RoomResponse
import presentation.dto.response.UserResponse
import java.util.UUID

class BookingController(
    private val service: BookingService,
) {
    suspend fun create(request: BookingCreateRequest): Result<BookingResponse> {
        return try {
            val booking = BookingCreateDto(
                userId = UUID.fromString(request.userId),
                roomId = UUID.fromString(request.roomId),
                startTime = LocalDateTime.parse(request.startTime),
                endTime = LocalDateTime.parse(request.endTime),
            )
            val created = service.create(booking)
            val response = BookingResponse(
                id = created.id.toString(),
                userId = created.userId.toString(),
                roomId = created.roomId.toString(),
                startTime = created.startTime.toString(),
                endTime = created.endTime.toString(),
                status = created.status.toString(),
//                createdAt = created.createdAt.toString()
            )
            Result.Success(response)
        } catch (e: Exception) {
            Result.Failure(e.localizedMessage)
        }
    }

    suspend fun getById(id: String): Result<BookingResponse> {
        return try {
            val booking = service.getById(UUID.fromString(id))
            val response = BookingResponse(
                id = booking.id.toString(),
                userId = booking.userId.toString(),
                roomId = booking.roomId.toString(),
                startTime = booking.startTime.toString(),
                endTime = booking.endTime.toString(),
                status = booking.status.toString(),
//                createdAt = booking.createdAt.toString()
            )
            Result.Success(response)
        } catch (e: Exception) {
            Result.Failure(e.localizedMessage)
        }
    }

    suspend fun getAll(): Result<List<BookingResponse>> {
        return try {
            val bookings = service.getAll()
            val resposne = bookings.map { it ->
                BookingResponse(
                    id = it.id.toString(),
                    userId = it.userId.toString(),
                    roomId = it.roomId.toString(),
                    startTime = it.startTime.toString(),
                    endTime = it.endTime.toString(),
                    status = it.status.toString(),
//                    createdAt = it.createdAt.toString()
                )
            }
            Result.Success(resposne)
        } catch (e: Exception) {
            Result.Failure(e.localizedMessage)
        }
    }

    suspend fun update(id: String, request: BookingUpdateRequest): Result<Boolean> {
        return try {
            val booking = BookingUpdateDto(
                roomId = if (request.roomId != null) UUID.fromString(request.roomId) else null,
                startTime = if (request.startTime != null) LocalDateTime.parse(request.startTime) else null,
                endTime = if (request.endTime != null) LocalDateTime.parse(request.endTime) else null,
                status = if (request.status != null) BookingStatus.valueOf(request.status) else null
            )
            val updated = service.update(UUID.fromString(id), booking)
            Result.Success(updated)
        } catch (e: Exception) {
            Result.Failure(e.localizedMessage)
        }
    }

    suspend fun deleteById(id: String): Result<Boolean> {
        return try {
            val deleted = service.deleteById(UUID.fromString(id))
            Result.Success(deleted)
        } catch (e: Exception) {
            Result.Failure(e.localizedMessage)
        }
    }
}
