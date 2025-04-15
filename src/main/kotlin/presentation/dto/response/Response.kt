package presentation.dto.response

import core.security.tokens.AccessToken
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Response(
    val user: UserResponse,
    @SerialName("access_token")
    val accessToken: AccessToken,
)