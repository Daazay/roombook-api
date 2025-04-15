package presentation.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class UserUpdateRequest(
    val email: String? = null,
    val password: String? = null,
)