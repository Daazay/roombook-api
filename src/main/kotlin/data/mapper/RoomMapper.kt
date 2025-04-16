package data.mapper

import data.entity.RoomEntity
import domain.model.RoomDto
import domain.model.RoomWithBuildingDto
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.statements.InsertStatement

fun InsertStatement<Number>.toRoomDto(): RoomDto {
    return RoomDto(
        id = this[RoomEntity.id].value,
        buildingId = this[RoomEntity.buildingId],
        name = this[RoomEntity.name],
        type = this[RoomEntity.type],
        status = this[RoomEntity.status],
        capacity = this[RoomEntity.capacity],
//        createdAt = this[RoomEntity.createdAt]
    )
}

fun ResultRow.toRoomDto(): RoomDto {
    return RoomDto(
        id = this[RoomEntity.id].value,
        buildingId = this[RoomEntity.buildingId],
        name = this[RoomEntity.name],
        type = this[RoomEntity.type],
        status = this[RoomEntity.status],
        capacity = this[RoomEntity.capacity],
//        createdAt = this[RoomEntity.createdAt]
    )
}

fun ResultRow.toRoomWithBuildingDto(): RoomWithBuildingDto {
    return RoomWithBuildingDto(
        id = this[RoomEntity.id].value,
        building = this.toBuildingDto(),
        name = this[RoomEntity.name],
        type = this[RoomEntity.type],
        status = this[RoomEntity.status],
        capacity = this[RoomEntity.capacity],
//        createdAt = this[RoomEntity.createdAt]
    )
}