package domain.model

import kotlinx.datetime.LocalDateTime
import java.util.UUID

enum class BookingStatus {
    CONFIRMED, PENDING, CANCELLED
}

data class BookingCreateDto(
    val userId: UUID,
    val roomId: UUID,
    val startTime: LocalDateTime,
    var endTime: LocalDateTime,
)

data class BookingUpdateDto(
    val roomId: UUID? = null,
    val startTime: LocalDateTime? = null,
    var endTime: LocalDateTime? = null,
    val status: BookingStatus? = null,
)

data class BookingDto(
    val id: UUID,
    val userId: UUID,
    val roomId: UUID,
    val startTime: LocalDateTime,
    var endTime: LocalDateTime,
    val status: BookingStatus,
//    val createdAt: LocalDateTime,
)

data class BookingWithUserAndRoomDto(
    val id: UUID,
    val user: UserDto,
    val room: RoomDto,
    val startTime: LocalDateTime,
    var endTime: LocalDateTime,
    val status: BookingStatus,
//    val createdAt: LocalDateTime,
)