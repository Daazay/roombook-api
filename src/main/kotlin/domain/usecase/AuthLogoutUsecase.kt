package domain.usecase

import domain.model.UserCreateDto
import domain.repository.RefreshTokenRepository
import domain.repository.UserRepository
import java.util.UUID

class AuthLogoutUsecase(
    private val userRepository: UserRepository,
    private val tokenRepository: RefreshTokenRepository,
) {
    suspend operator fun invoke(id: UUID): Int {
        return tokenRepository.deleteByUserId(id)
    }
}