package core.config

import io.ktor.server.application.*
import io.ktor.server.sessions.*
import kotlinx.serialization.Serializable

@Serializable
data class JWTToken(val token: String)

fun Application.configureSessions() {
    val expiresIn = environment.config.property("jwt.refresh.expiresIn").getString().toLong()
    install(Sessions) {
        cookie<JWTToken>("refresh_token") {
            cookie.path = "/"
            cookie.maxAgeInSeconds = expiresIn
            cookie.httpOnly = true
        }
    }
}