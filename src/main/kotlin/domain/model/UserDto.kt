package domain.model

import kotlinx.datetime.LocalDateTime
import java.util.UUID

enum class UserRole{
    USER, ADMIN,
}

data class UserDto(
    val id: UUID,
    val username: String,
    val email: String,
    val password: String,
    val salt: String,
    val role: UserRole,
//    val createdAt: LocalDateTime,
)

data class UserCreateDto(
    val username: String,
    val email: String,
    val password: String,
    val salt: String? = null,
)

data class UserUpdateDto(
    val email: String?,
    val password: String?,
    val salt: String?,
)
