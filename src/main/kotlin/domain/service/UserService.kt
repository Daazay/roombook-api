package domain.service

import core.security.hashing.HashingService
import domain.model.UserDto
import domain.model.UserUpdateDto
import domain.repository.UserRepository
import java.util.UUID

class UserService(
    private val userRepository: UserRepository,
    private val hashingService: HashingService,
) {
    suspend fun getById(id: UUID): UserDto {
        val user = userRepository.findById(id)
            ?: throw IllegalArgumentException("User not found.")

        return user
    }

    suspend fun getAll(): List<UserDto> {
        return userRepository.findAll()
    }

    suspend fun update(id: UUID, user: UserUpdateDto): Boolean {
        var updateStmt = user.copy()
        if (user.email != null) {
            require(user.email.contains(Regex("^\\S+@\\S+\\.\\S+"))) {
                "invalid mail"
            }
        }
        if (user.password != null) {
            require(user.password.isNotBlank() && user.password.length >= 8) {
                "password must be at least 8 characters long"
            }
            val saltedHash = hashingService.generateSaltedHash(updateStmt.password!!)
            updateStmt = updateStmt.copy(
                password = saltedHash.hash,
                salt = saltedHash.salt
            )
        }

        return userRepository.update(id, updateStmt)
    }

    suspend fun deleteById(id: UUID): Boolean {
        return userRepository.deleteById(id)
    }
}