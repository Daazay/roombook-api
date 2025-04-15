package domain.repository

import domain.model.TokenDto
import java.util.UUID

interface RefreshTokenRepository {
    suspend fun upsert(userId: UUID, token: String): TokenDto

    suspend fun deleteByUserId(userId: UUID): Int
}