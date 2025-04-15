package presentation.routes

import core.config.JWTToken
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import presentation.dto.request.AuthLoginRequest
import presentation.dto.request.AuthSignupRequest
import core.utils.Result
import io.ktor.server.auth.jwt.*
import io.ktor.server.sessions.*
import presentation.controller.AuthController
import java.util.*

fun Route.configureAuthRoute(
    controller: AuthController,
) {
    route("/auth") {
        post("/signup") {
            val request = call.receiveNullable<AuthSignupRequest>()
                ?: return@post call.respond(HttpStatusCode.BadRequest)
            when(val result = controller.signup(request)) {
                is Result.Failure -> {
                    call.respond(HttpStatusCode.BadRequest, mapOf("error" to result.message!!))
                }
                is Result.Success -> {
                    call.sessions.set(JWTToken(result.data!!.second))
                    call.respond(HttpStatusCode.OK, result.data.first)
                }
            }
        }
        post("/login") {
            val request = call.receiveNullable<AuthLoginRequest>()
                ?: return@post call.respond(HttpStatusCode.BadRequest)
            when(val result = controller.login(request)) {
                is Result.Failure -> {
                    call.respond(HttpStatusCode.BadRequest, mapOf("error" to result.message!!))
                }
                is Result.Success -> {
                    call.sessions.set(JWTToken(result.data!!.second))
                    call.respond(HttpStatusCode.OK, result.data.first)
                }
            }
        }
        authenticate("basic") {
            get("/logout") {
                val principal = call.principal<JWTPrincipal>()
                    ?: return@get call.respond(HttpStatusCode.BadRequest)
                val userId = principal.getClaim("id", UUID::class)
                    ?: return@get call.respond(HttpStatusCode.BadRequest)
                when(val result = controller.logout(userId)) {
                    is Result.Failure -> {
                        call.respond(HttpStatusCode.BadRequest, mapOf("error" to result.message!!))
                    }
                    is Result.Success -> {
                        call.respond(HttpStatusCode.OK)
                    }
                }
            }
        }
    }
}