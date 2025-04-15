package data.mapper

import data.entity.RefreshTokenEntity
import domain.model.TokenDto
import org.jetbrains.exposed.sql.statements.UpsertStatement

fun UpsertStatement<Long>.toTokenDto(): TokenDto {
    return TokenDto(
        userId = this[RefreshTokenEntity.id].value,
        token = this[RefreshTokenEntity.token],
    )
}