package data.mapper

import data.entity.UserEntity
import domain.model.UserDto
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.statements.InsertStatement

fun InsertStatement<Number>.toUserDto(): UserDto {
    return UserDto(
        id = this[UserEntity.id].value,
        username = this[UserEntity.username],
        email = this[UserEntity.email],
        role = this[UserEntity.role],
        password = this[UserEntity.password],
        salt = this[UserEntity.salt],
//        createdAt = this[UserEntity.createdAt]
    )
}

fun ResultRow.toUserDto(): UserDto {
    return UserDto(
        id = this[UserEntity.id].value,
        username = this[UserEntity.username],
        email = this[UserEntity.email],
        role = this[UserEntity.role],
        password = this[UserEntity.password],
        salt = this[UserEntity.salt],
//        createdAt = this[UserEntity.createdAt]
    )
}