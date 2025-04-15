package data.entity

import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.ReferenceOption
import java.util.UUID

object RefreshTokenEntity: IdTable<UUID>("refresh_tokens") {
    override val id = reference("user_id", UserEntity.id, onDelete = ReferenceOption.CASCADE).uniqueIndex()

    val token = text("refresh_token")
}