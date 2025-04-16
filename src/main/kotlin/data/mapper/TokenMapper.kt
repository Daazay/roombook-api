package data.mapper

import data.entity.RefreshTokenEntity
import data.entity.UserEntity
import domain.model.TokenDto
import domain.model.UserDto
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.statements.UpsertStatement

fun InsertStatement<Number>.toTokenDto(): TokenDto {
    return TokenDto(
        userId = this[RefreshTokenEntity.id].value,
        token = this[RefreshTokenEntity.token],
    )
}

fun UpsertStatement<Long>.toTokenDto(): TokenDto {
    return TokenDto(
        userId = this[RefreshTokenEntity.id].value,
        token = this[RefreshTokenEntity.token],
    )
}

fun ResultRow.toTokenDto(): TokenDto {
    return TokenDto(
        userId = this[RefreshTokenEntity.id].value,
        token = this[RefreshTokenEntity.token],
    )
}