package domain.repository

import domain.model.RoomCreateDto
import domain.model.RoomDto
import domain.model.RoomUpdateDto
import java.util.UUID

interface RoomRepository {
    suspend fun insert(room: RoomCreateDto): RoomDto

    suspend fun findById(id: UUID): RoomDto?
    suspend fun findByBuildingId(buildingId: UUID): List<RoomDto>
    suspend fun findAll(): List<RoomDto>

    suspend fun update(id: UUID, room: RoomUpdateDto): Boolean

    suspend fun deleteById(id: UUID): Boolean
    suspend fun deleteByBuildingId(id: UUID): Int
    suspend fun deleteAll(): Int
}
