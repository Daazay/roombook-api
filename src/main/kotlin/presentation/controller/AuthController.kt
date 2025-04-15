package presentation.controller

import core.security.tokens.RefreshToken
import domain.model.UserCreateDto
import presentation.dto.request.AuthLoginRequest
import presentation.dto.request.AuthSignupRequest
import presentation.dto.response.UserResponse
import core.utils.Result
import domain.service.AuthService
import presentation.dto.response.Response
import java.util.UUID

class AuthController(
    private val service: AuthService,
) {
    suspend fun signup(request: AuthSignupRequest): Result<Pair<Response, RefreshToken>> {
        return try {
            val user = UserCreateDto(
                username = request.username,
                email = request.email,
                password = request.password,
            )
            val result = service.signup(user)
            val response = Pair(
                first = Response(
                    user = UserResponse(
                        id = result.first.id.toString(),
                        username = result.first.username,
                        email = result.first.email,
                        role = result.first.role.toString(),
                    ),
                    accessToken = result.second.first
                ),
                second = result.second.second
            )
            Result.Success(response)
        } catch (e: Exception) {
            Result.Failure(e.localizedMessage)
        }
    }

    suspend fun login(request: AuthLoginRequest): Result<Pair<Response, RefreshToken>> {
        return try {
            val result = service.login(request.username, request.password)

            val response = Pair(
                first = Response(
                    user = UserResponse(
                        id = result.first.id.toString(),
                        username = result.first.username,
                        email = result.first.email,
                        role = result.first.role.toString(),
                    ),
                    accessToken = result.second.first
                ),
                second = result.second.second
            )
            Result.Success(response)
        } catch (e: Exception) {
            Result.Failure(e.localizedMessage)
        }
    }

    suspend fun logout(id: UUID): Result<Boolean> {
        return try {
            val result = service.logout(id)
            Result.Success(result > 0)
        } catch (e: Exception) {
            Result.Failure(e.localizedMessage)
        }
    }
}
