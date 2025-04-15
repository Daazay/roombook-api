package core.config

import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database

fun Application.configureDatabase() {
    val url = environment.config.property("storage.url").getString()
    val driver = environment.config.property("storage.driver").getString()
    val user = environment.config.property("storage.user").getString()
    val password = environment.config.property("storage.password").getString()

    Database.connect(
        url = url,
        driver = driver,
        user = user,
        password = password
    )
}