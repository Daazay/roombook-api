package data.mapper

import data.entity.BuildingEntity
import domain.model.BuildingDto
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.statements.InsertStatement

fun InsertStatement<Number>.toBuildingDto(): BuildingDto {
    return BuildingDto(
        id = this[BuildingEntity.id].value,
        name = this[BuildingEntity.name],
        address = this[BuildingEntity.address],
//        createdAt = this[BuildingEntity.createdAt]
    )
}

fun ResultRow.toBuildingDto(): BuildingDto {
    return BuildingDto(
        id = this[BuildingEntity.id].value,
        name = this[BuildingEntity.name],
        address = this[BuildingEntity.address],
//        createdAt = this[BuildingEntity.createdAt]
    )
}