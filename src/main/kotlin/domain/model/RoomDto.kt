package domain.model

import kotlinx.datetime.LocalDateTime
import java.util.UUID

enum class RoomType {
    LECTURE, MEETING, LAB
}

enum class RoomStatus {
    FREE, OCCUPIED, UNAVAILABLE,
}

data class RoomCreateDto(
    val buildingId: UUID,
    val name: String,
    val type: RoomType,
    val capacity: Int,
    val status: RoomStatus,
)

data class RoomUpdateDto(
    val name: String? = null,
    val type: RoomType? = null,
    val capacity: Int? = null,
    val status: RoomStatus? = null,
)

data class RoomDto(
    val id: UUID,
    val buildingId: UUID,
    val name: String,
    val type: RoomType,
    val capacity: Int,
    val status: RoomStatus,
    val createdAt: LocalDateTime,
)

data class RoomWithBuildingDto(
    val id: UUID,
    val building: BuildingDto,
    val name: String,
    val type: RoomType,
    val capacity: Int,
    val status: RoomStatus,
    val createdAt: LocalDateTime,
)