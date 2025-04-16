package data.repository

import data.entity.RoomEntity
import data.entity.UserEntity
import data.mapper.toRoomDto
import domain.model.RoomCreateDto
import domain.model.RoomDto
import domain.model.RoomUpdateDto
import domain.repository.RoomRepository
import data.utils.tranzaction
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import java.util.UUID
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toKotlinLocalDateTime

class RoomRepositoryImpl : RoomRepository {
    init {
        tranzaction {
            SchemaUtils.create(RoomEntity)
        }
    }

    override suspend fun insert(room: RoomCreateDto): RoomDto = tranzaction {
        if (RoomEntity.selectAll().where { (RoomEntity.buildingId eq room.buildingId) and (RoomEntity.name eq room.name) }.count() > 0) {
            throw IllegalArgumentException("Room with same name already exist.")
        }

        RoomEntity.insert {
            it[buildingId] = room.buildingId
            it[name] = room.name
            it[type] = room.type
            it[capacity] = room.capacity
            it[status] = room.status
        }.toRoomDto()
    }

    override suspend fun findById(id: UUID): RoomDto? = tranzaction {
        RoomEntity.selectAll()
            .where { RoomEntity.id eq id }
            .singleOrNull()
            ?.toRoomDto()
    }

    override suspend fun findByBuildingId(buildingId: UUID): List<RoomDto> = tranzaction {
        RoomEntity.selectAll()
            .where { RoomEntity.buildingId eq buildingId }
            .map { it.toRoomDto() }
    }

    override suspend fun findAll(): List<RoomDto> = tranzaction {
        RoomEntity.selectAll()
            .map { it.toRoomDto() }
    }

    override suspend fun update(id: UUID, room: RoomUpdateDto): Boolean = tranzaction {
        val count = RoomEntity.update({ RoomEntity.id eq id }) {
            room.name?.let { name -> it[RoomEntity.name] = name }
            room.type?.let { type -> it[RoomEntity.type] = type }
            room.capacity?.let { capacity -> it[RoomEntity.capacity] = capacity }
            room.status?.let { status -> it[RoomEntity.status] = status }
        }
        if (count == 0) {
            throw IllegalArgumentException("Room not found")
        }
        true
    }

    override suspend fun deleteById(id: UUID): Boolean = tranzaction {
        val count = RoomEntity.deleteWhere { RoomEntity.id eq id }
        if (count == 0) {
            throw IllegalArgumentException("Room not found")
        }
        count > 0
    }

    override suspend fun deleteByBuildingId(id: UUID): Int = tranzaction {
        RoomEntity.deleteWhere { RoomEntity.buildingId eq id }
    }

    override suspend fun deleteAll(): Int = tranzaction {
        RoomEntity.deleteAll()
    }
}
