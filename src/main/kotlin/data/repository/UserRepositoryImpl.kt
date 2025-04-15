package data.repository

import data.entity.UserEntity
import data.mapper.toUserDto
import data.utils.tranzaction
import domain.model.UserCreateDto
import domain.model.UserDto
import domain.model.UserUpdateDto
import domain.repository.UserRepository
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import java.util.*

class UserRepositoryImpl: UserRepository {
    init {
        tranzaction {
            SchemaUtils.create(UserEntity)
        }
    }

    override suspend fun insert(user: UserCreateDto): UserDto = tranzaction {
        if (UserEntity.selectAll().where { UserEntity.username eq user.username }.count() > 0) {
            throw IllegalArgumentException("User with such username already exists.")
        }

        UserEntity.insert {
            it[UserEntity.username] = user.username
            it[UserEntity.email] = user.email
            it[UserEntity.password] = user.password
            it[UserEntity.salt] = user.salt!!
        }.toUserDto()
    }

    override suspend fun findById(id: UUID): UserDto? = tranzaction {
        UserEntity
            .selectAll()
            .where { UserEntity.id eq id }
            .singleOrNull()
            ?.toUserDto()
    }


    override suspend fun findByUsername(username: String): UserDto? = tranzaction {
        UserEntity
            .selectAll()
            .where { UserEntity.username eq username }
            .singleOrNull()
            ?.toUserDto()
    }

    override suspend fun findAll(): List<UserDto> = tranzaction {
        UserEntity
            .selectAll()
            .map { it.toUserDto() }
    }

    override suspend fun update(id: UUID, user: UserUpdateDto): Boolean = tranzaction {
        val count = UserEntity.update({ UserEntity.id eq id}) {
            user.email?.let { email -> it[UserEntity.email] = email }
            user.password?.let { password -> it[UserEntity.password] = password }
            user.salt?.let { salt -> it[UserEntity.salt] = salt }
        }
        if (count > 0) {
            throw IllegalArgumentException("User not found")
        }
        true
    }

    override suspend fun deleteById(id: UUID): Boolean = tranzaction {
        val count = UserEntity.deleteWhere {
            UserEntity.id eq id
        }
        if (count > 0) {
            throw IllegalArgumentException("User not found")
        }
        true
    }

    override suspend fun deleteAll(): Int = tranzaction {
        UserEntity.deleteAll()
    }
}
