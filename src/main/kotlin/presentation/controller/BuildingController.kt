package presentation.controller

import core.utils.Result
import domain.model.BuildingCreateDto
import domain.model.BuildingDto
import domain.model.BuildingUpdateDto
import domain.service.BuildingService
import presentation.dto.request.BuildingCreateRequest
import presentation.dto.request.BuildingUpdateRequest
import presentation.dto.response.BuildingResponse
import java.util.UUID

class BuildingController(
    private val service: BuildingService,
) {
    suspend fun create(request: BuildingCreateRequest): Result<BuildingResponse> {
        return try {
            val building = BuildingCreateDto(
                name = request.name,
                address = request.address
            )
            val created = service.create(building)
            val response = BuildingResponse(
                id = created.id.toString(),
                name = created.name,
                address = created.address,
                createdAt = created.createdAt.toString()
            )
            Result.Success(response)
        } catch (e: Exception) {
            Result.Failure(e.localizedMessage)
        }
    }

    suspend fun getById(id: String): Result<BuildingResponse> {
        return try {
            val building = service.getById(UUID.fromString(id))
            val response = BuildingResponse(
                id = building.id.toString(),
                name = building.name,
                address = building.address,
                createdAt = building.createdAt.toString()
            )
            Result.Success(response)
        } catch (e: Exception) {
            Result.Failure(e.localizedMessage)
        }
    }

    suspend fun getAll(): Result<List<BuildingResponse>> {
        return try {
            val buildings = service.getAll()
            val response = buildings.map { building ->
                BuildingResponse(
                    id = building.id.toString(),
                    name = building.name,
                    address = building.address,
                    createdAt = building.createdAt.toString()
                )
            }
            Result.Success(response)
        } catch (e: Exception) {
            Result.Failure(e.localizedMessage)
        }
    }

    suspend fun update(id: String, request: BuildingUpdateRequest): Result<Boolean> {
        return try {
            val building = BuildingUpdateDto(
                name = request.name,
                address = request.address
            )
            val updated = service.update(UUID.fromString(id), building)
            Result.Success(updated)
        } catch (e: Exception) {
            Result.Failure(e.localizedMessage)
        }
    }

    suspend fun deleteById(id: String): Result<Boolean> {
        return try {
            val deleted = service.deleteById(UUID.fromString(id))
            Result.Success(deleted)
        } catch (e: Exception) {
            Result.Failure(e.localizedMessage)
        }
    }
}
