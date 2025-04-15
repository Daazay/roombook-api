package presentation.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class BuildingCreateRequest(
    val name: String,
    val address: String,
)

@Serializable
data class BuildingUpdateRequest(
    val name: String? = null,
    val address: String? = null,
)