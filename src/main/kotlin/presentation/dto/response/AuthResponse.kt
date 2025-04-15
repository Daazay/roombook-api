package presentation.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val user: UserResponse,
    @SerialName("access_token")
    val accessToken: String,
)