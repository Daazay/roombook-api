package presentation.controller

import core.utils.Result
import domain.model.UserUpdateDto
import domain.service.UserService
import presentation.dto.request.UserUpdateRequest
import presentation.dto.response.UserResponse
import java.util.UUID

class UserController(
    private val service: UserService,
) {
    suspend fun getById(id: String): Result<UserResponse> {
        return try {
            val result = service.getById(UUID.fromString(id))
            val response = UserResponse(
                id = result.id.toString(),
                email = result.email,
                username =  result.username,
                role = result.role.toString(),
            )
            Result.Success(response)
        } catch (e: Exception) {
            Result.Failure(e.localizedMessage)
        }
    }

    suspend fun getAll(): Result<List<UserResponse>> {
        return try {
            val result = service.getAll()
            val response = result.map { it ->
                UserResponse(
                    id = it.id.toString(),
                    email = it.email,
                    username =  it.username,
                    role = it.role.toString(),
                )
            }
            Result.Success(response)
        } catch (e: Exception) {
            Result.Failure(e.localizedMessage)
        }
    }

    suspend fun update(id: String, user: UserUpdateRequest): Result<Boolean> {
        return try {
            val updateDto = UserUpdateDto(
                email = user.email,
                password = user.password,
                salt = null,
            )
            val result = service.update(UUID.fromString(id), updateDto)
            Result.Success(result)
        } catch (e: Exception) {
            Result.Failure(e.localizedMessage)
        }
    }

    suspend fun deleteById(id: String): Result<Boolean> {
        return try {
            val result = service.deleteById(UUID.fromString(id))
            Result.Success(result)
        } catch (e: Exception) {
            Result.Failure(e.localizedMessage)
        }
    }
}