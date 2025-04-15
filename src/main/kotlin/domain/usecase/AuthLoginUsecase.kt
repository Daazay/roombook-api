package domain.usecase

import core.security.hashing.SaltedHash
import core.security.hashing.impl.SHA256HashingService
import core.security.tokens.AccessToken
import core.security.tokens.RefreshToken
import core.security.tokens.TokenClaim
import core.security.tokens.TokenConfig
import core.security.tokens.impl.JWTTokenService
import domain.model.UserDto
import domain.repository.RefreshTokenRepository
import domain.repository.UserRepository

class AuthLoginUsecase(
    private val userRepository: UserRepository,
    private val tokenRepository: RefreshTokenRepository,
    private val accessConfig: TokenConfig,
    private val refreshConfig: TokenConfig,
) {
    private val hashingService = SHA256HashingService()
    private val tokenService = JWTTokenService()

    suspend operator fun invoke(username: String, password: String): Pair<UserDto, Pair<AccessToken, RefreshToken>> {
        require(username.isNotBlank() && username.length >= 4) {
            "username must be at least 4 characters long"
        }
        require(password.isNotBlank() && password.length >= 8) {
            "password must be at least 8 characters long"
        }

        val found = userRepository.findByUsername(username)
        require(found != null) {
            "User not found"
        }

        require(hashingService.verify(password, SaltedHash(found.password, found.salt))) {
            "Invalid password"
        }

        val tokens = tokenService.generateTokens(
            accessConfig,
            refreshConfig,
            TokenClaim("id", found.id.toString()),
            TokenClaim("role", found.role.toString())
        )

        tokenRepository.upsert(found.id, tokens.second)

        return Pair(found, tokens)
    }
}