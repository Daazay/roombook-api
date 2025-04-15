package presentation.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RoomResponse(
    val id: String,
    @SerialName("building_id")
    val buildingId: String,
    val name: String,
    val type: String,
    val capacity: Int,
    val status: String,
    @SerialName("created_at")
    val createdAt: String,
)

@Serializable
data class RoomWithBuildingResponse(
    val id: String,
    @SerialName("building")
    val building: BuildingResponse,
    val name: String,
    val type: String,
    val capacity: Int,
    val status: String,
    @SerialName("created_at")
    val createdAt: String,
)