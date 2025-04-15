package presentation.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class AuthSignupRequest(
    val username: String,
    val email: String,
    val password: String,
)

@Serializable
data class AuthLoginRequest(
    val username: String,
    val password: String,
)