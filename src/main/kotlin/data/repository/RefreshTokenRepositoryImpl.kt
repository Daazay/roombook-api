package data.repository

import data.entity.RefreshTokenEntity
import data.entity.UserEntity
import data.mapper.toTokenDto
import data.utils.tranzaction
import domain.model.TokenDto
import domain.repository.RefreshTokenRepository
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import java.util.*

class RefreshTokenRepositoryImpl: RefreshTokenRepository {
    init {
        tranzaction {
            SchemaUtils.create(RefreshTokenEntity)
        }
    }

    override suspend fun upsert(userId: UUID, token: String): TokenDto = tranzaction {
        if (UserEntity.selectAll().where { UserEntity.id eq userId }.empty()) {
            throw IllegalArgumentException("User not found.")
        }

        if (RefreshTokenEntity.selectAll().where { RefreshTokenEntity.id eq userId }.empty()) {
            RefreshTokenEntity.insert {
                it[id] = userId
                it[RefreshTokenEntity.token] = token
            }.toTokenDto()
        } else {
            val count = RefreshTokenEntity.update({ RefreshTokenEntity.id eq userId }) {
                it[RefreshTokenEntity.token] = token
            }
            RefreshTokenEntity.selectAll().where { RefreshTokenEntity.id eq userId }
                .single().toTokenDto()
        }
    }

    override suspend fun deleteByUserId(userId: UUID): Int = tranzaction {
        RefreshTokenEntity.deleteWhere {
            RefreshTokenEntity.id eq userId
        }
    }
}