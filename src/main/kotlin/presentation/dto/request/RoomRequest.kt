package presentation.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RoomCreateRequest(
    @SerialName("building_id")
    val buildingId: String,
    val name: String,
    val type: String,
    val capacity: Int,
)

@Serializable
data class RoomUpdateRequest(
    val name: String? = null,
    val type: String? = null,
    val capacity: Int? = null,
    val status: String? = null,
)