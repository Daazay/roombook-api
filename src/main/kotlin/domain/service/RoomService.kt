package domain.service

import domain.model.RoomCreateDto
import domain.model.RoomDto
import domain.model.RoomUpdateDto
import domain.repository.RoomRepository
import java.util.UUID

class RoomService(
    private val roomRepository: RoomRepository,
) {
    suspend fun create(room: RoomCreateDto): RoomDto {
        require(room.name.isNotBlank()) {
            "room name can not be blank."
        }
        require(room.capacity in 1..99) {
            "room capacity must be greater than 0 and lesser than 100"
        }
        return roomRepository.insert(room)
    }

    suspend fun getById(id: UUID): RoomDto {
        return roomRepository.findById(id)
            ?: throw IllegalArgumentException("Room not found.")
    }

    suspend fun getByBuildingId(buildingId: UUID): List<RoomDto> {
        return roomRepository.findByBuildingId(buildingId)
    }

    suspend fun getAll(): List<RoomDto> {
        return roomRepository.findAll()
    }

    suspend fun update(id: UUID, room: RoomUpdateDto): Boolean {
        if (room.name != null) {
            require(room.name.isNotBlank()) {
                "room name can not be blank."
            }
        }
        if (room.capacity != null) {
            require(room.capacity in 1..99) {
                "room capacity must be greater than 0 and lesser than 100"
            }
        }
        return roomRepository.update(id, room)
    }

    suspend fun deleteById(id: UUID): Boolean {
        return roomRepository.deleteById(id)
    }

    suspend fun deleteByBuildingId(buildingId: UUID): Int {
        return roomRepository.deleteByBuildingId(buildingId)
    }

    suspend fun deleteAll(): Int {
        return roomRepository.deleteAll()
    }
}
