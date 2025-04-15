package domain.repository

import domain.model.BuildingCreateDto
import domain.model.BuildingDto
import domain.model.BuildingUpdateDto
import java.util.UUID

interface BuildingRepository {
    suspend fun insert(building: BuildingCreateDto): BuildingDto
    suspend fun findById(id: UUID): BuildingDto?
    suspend fun findAll(): List<BuildingDto>
    suspend fun update(id: UUID, building: BuildingUpdateDto): Boolean
    suspend fun deleteById(id: UUID): Boolean
}
