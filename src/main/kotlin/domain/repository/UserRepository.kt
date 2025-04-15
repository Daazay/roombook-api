package domain.repository

import domain.model.UserCreateDto
import domain.model.UserDto
import domain.model.UserUpdateDto
import java.util.UUID

interface UserRepository {
    suspend fun insert(user: UserCreateDto): UserDto

    suspend fun findById(id: UUID): UserDto?
    suspend fun findByUsername(username: String): UserDto?
    suspend fun findAll(): List<UserDto>

    suspend fun update(id: UUID, user: UserUpdateDto): Boolean

    suspend fun deleteById(id: UUID): Boolean
    suspend fun deleteAll(): Int
}
