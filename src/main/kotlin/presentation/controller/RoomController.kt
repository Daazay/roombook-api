package presentation.controller

import core.utils.Result
import domain.model.*
import domain.service.RoomService
import presentation.dto.request.RoomCreateRequest
import presentation.dto.request.RoomUpdateRequest
import presentation.dto.response.BuildingResponse
import presentation.dto.response.RoomResponse
import java.util.UUID

class RoomController(
    private val service: RoomService,
) {
    suspend fun create(room: RoomCreateRequest): Result<RoomResponse> {
        return try {
            val roomDto = RoomCreateDto(
                buildingId = UUID.fromString(room.buildingId),
                name = room.name,
                capacity = room.capacity,
                type = RoomType.valueOf(room.type),
                status = RoomStatus.FREE,
            )
            val created = service.create(roomDto)
            val response = RoomResponse(
                id = created.id.toString(),
                buildingId = created.buildingId.toString(),
                name = created.name,
                status = created.status.toString(),
                type = created.type.toString(),
                capacity = created.capacity,
//                createdAt = created.createdAt.toString()
            )

            Result.Success(response)
        } catch (e: Exception) {
            Result.Failure(e.localizedMessage)
        }
    }

    suspend fun getById(id: String): Result<RoomResponse> {
        return try {
            val room = service.getById(UUID.fromString(id))
            val response = RoomResponse(
                id = room.id.toString(),
                buildingId = room.buildingId.toString(),
                name = room.name,
                status = room.status.toString(),
                type = room.type.toString(),
                capacity = room.capacity,
//                createdAt = room.createdAt.toString()
            )
            Result.Success(response)
        } catch (e: Exception) {
            Result.Failure(e.localizedMessage)
        }
    }

    suspend fun getByBuildingId(buildingId: String): Result<List<RoomResponse>> {
        return try {
            val rooms = service.getByBuildingId(UUID.fromString(buildingId))
            val response = rooms.map { room ->
                RoomResponse(
                    id = room.id.toString(),
                    buildingId = room.buildingId.toString(),
                    name = room.name,
                    status = room.status.toString(),
                    type = room.type.toString(),
                    capacity = room.capacity,
//                    createdAt = room.createdAt.toString()
                )
            }
            Result.Success(response)
        } catch (e: Exception) {
            Result.Failure(e.localizedMessage)
        }
    }

    suspend fun getAll(): Result<List<RoomResponse>> {
        return try {
            val rooms = service.getAll()
            val response = rooms.map { room ->
                RoomResponse(
                    id = room.id.toString(),
                    buildingId = room.buildingId.toString(),
                    name = room.name,
                    status = room.status.toString(),
                    type = room.type.toString(),
                    capacity = room.capacity,
//                    createdAt = room.createdAt.toString()
                )
            }
            Result.Success(response)
        } catch (e: Exception) {
            Result.Failure(e.localizedMessage)
        }
    }

    suspend fun update(id: String, room: RoomUpdateRequest): Result<Boolean> {
        return try {
            val roomDto = RoomUpdateDto(
                name = room.name,
                capacity = room.capacity,
                type = if (room.type != null) RoomType.valueOf(room.type) else null,
                status = RoomStatus.FREE,
            )
            val status = service.update(UUID.fromString(id), roomDto)
            Result.Success(status)
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
