package domain.model

import java.util.UUID

data class TokenDto(
    val userId: UUID,
    val token: String,
)