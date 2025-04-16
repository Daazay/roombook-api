package domain.model

import kotlinx.datetime.LocalDateTime
import java.util.UUID

data class BuildingCreateDto(
    val name: String,
    val address: String,
)

data class BuildingUpdateDto(
    val name: String? = null,
    val address: String? = null,
)

data class BuildingDto(
    val id: UUID,
    val name: String,
    val address: String,
//    val createdAt: LocalDateTime,
)
