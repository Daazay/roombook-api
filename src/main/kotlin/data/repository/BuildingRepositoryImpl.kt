package data.repository

import data.entity.BuildingEntity
import data.entity.UserEntity
import data.mapper.toBuildingDto
import domain.model.BuildingDto
import domain.repository.BuildingRepository
import data.utils.tranzaction
import domain.model.BuildingCreateDto
import domain.model.BuildingUpdateDto
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import java.util.UUID

class BuildingRepositoryImpl : BuildingRepository {
    init {
        tranzaction {
            SchemaUtils.create(BuildingEntity)
        }
    }

    override suspend fun insert(building: BuildingCreateDto): BuildingDto = tranzaction {
        if (BuildingEntity.selectAll().where { BuildingEntity.name eq building.name }.count() > 0) {
            throw IllegalArgumentException("Building with such name already exists.")
        }

        BuildingEntity.insert {
            it[name] = building.name
            it[address] = building.address
        }.toBuildingDto()
    }

    override suspend fun findById(id: UUID): BuildingDto? = tranzaction {
        BuildingEntity.selectAll()
            .where { BuildingEntity.id eq id }
            .singleOrNull()
            ?.toBuildingDto()
    }

    override suspend fun findAll(): List<BuildingDto> = tranzaction {
        BuildingEntity.selectAll()
            .map { it.toBuildingDto() }
    }

    override suspend fun update(id: UUID, building: BuildingUpdateDto): Boolean = tranzaction {
        val count = BuildingEntity.update({ BuildingEntity.id eq id }) {
            building.name?.let { name -> it[BuildingEntity.name] = name }
            building.address?.let { address -> it[BuildingEntity.address] = address }
        }
        if (count == 0) {
            throw IllegalArgumentException("Building not found")
        }
        true
    }

    override suspend fun deleteById(id: UUID): Boolean = tranzaction {
        val count = BuildingEntity.deleteWhere { BuildingEntity.id eq id }
        if (count == 0) {
            throw IllegalArgumentException("Building not found")
        }
        true
    }
}
