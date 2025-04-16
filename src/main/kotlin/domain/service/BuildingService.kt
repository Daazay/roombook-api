package domain.service

import domain.model.BuildingCreateDto
import domain.model.BuildingDto
import domain.model.BuildingUpdateDto
import domain.repository.BuildingRepository
import java.util.UUID

class BuildingService(
    private val buildingRepository: BuildingRepository,
) {
    suspend fun create(building: BuildingCreateDto): BuildingDto {
        require(building.name.isNotBlank()) {
            "building name can not be blank."
        }
        return buildingRepository.insert(building)
    }

    suspend fun getById(id: UUID): BuildingDto {
        return buildingRepository.findById(id)
            ?: throw IllegalArgumentException("Building not found.")
    }

    suspend fun getAll(): List<BuildingDto> {
        return buildingRepository.findAll()
    }

    suspend fun update(id: UUID, building: BuildingUpdateDto): Boolean {
        return buildingRepository.update(id, building)
    }

    suspend fun deleteById(id: UUID): Boolean {
        return buildingRepository.deleteById(id)
    }
}
