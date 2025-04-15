package domain.service

import core.security.hashing.SaltedHash
import core.security.hashing.impl.SHA256HashingService
import core.security.tokens.AccessToken
import core.security.tokens.RefreshToken
import core.security.tokens.TokenClaim
import core.security.tokens.TokenConfig
import core.security.tokens.impl.JWTTokenService
import domain.model.UserCreateDto
import domain.model.UserDto
import domain.repository.RefreshTokenRepository
import domain.repository.UserRepository
import java.util.*

class AuthService(
    private val userRepository: UserRepository,
    private val tokenRepository: RefreshTokenRepository,
    private val accessConfig: TokenConfig,
    private val refreshConfig: TokenConfig,
) {
    private val hashingService = SHA256HashingService()
    private val tokenService = JWTTokenService()

    suspend fun signup(user: UserCreateDto): Pair<UserDto, Pair<AccessToken, RefreshToken>> {
        require(user.username.isNotBlank() && user.username.length >= 4) {
            "username must be at least 4 characters long"
        }
        require(user.email.isNotBlank()) {
            "email must be specified"
        }
        require(user.email.contains(Regex("^\\S+@\\S+\\.\\S+"))) {
            "invalid mail"
        }
        require(user.password.isNotBlank() && user.password.length >= 8) {
            "password must be at least 8 characters long"
        }

        val saltedHash = hashingService.generateSaltedHash(user.password)

        val userInsert = user.copy(
            password = saltedHash.hash,
            salt = saltedHash.salt
        )

        var user = userRepository.insert(userInsert)
        val tokens = tokenService.generateTokens(
            accessConfig,
            refreshConfig,
            TokenClaim("id", user.id.toString()),
            TokenClaim("role", user.role.toString())
        )

        tokenRepository.upsert(user.id, tokens.second)

        return Pair(user, tokens)
    }

    suspend fun login(username: String, password: String): Pair<UserDto, Pair<AccessToken, RefreshToken>> {
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

    suspend fun logout(id: UUID): Int {
        return tokenRepository.deleteByUserId(id)
    }
}