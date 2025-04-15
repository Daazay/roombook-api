package presentation.routes

import core.config.JWTToken
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import core.security.tokens.TokenConfig
import data.repository.RefreshTokenRepositoryImpl
import data.repository.UserRepositoryImpl
import domain.usecase.AuthLoginUsecase
import domain.usecase.AuthLogoutUsecase
import domain.usecase.AuthSignupUsecase
import presentation.dto.request.AuthLoginRequest
import presentation.dto.request.AuthSignupRequest
import core.utils.Result
import io.ktor.server.auth.jwt.*
import io.ktor.server.sessions.*
import presentation.dto.response.Response
import java.util.*

fun Route.configureAuthRoute() {
    val accessConfig = TokenConfig(
        issuer = application.environment.config.property("jwt.issuer").getString(),
        audience = application.environment.config.property("jwt.audience").getString(),
        expiresIn = application.environment.config.property("jwt.access.expiresIn").getString().toLong(),
        secret = application.environment.config.property("jwt.access.secret").getString(),
    )
    val refreshConfig = TokenConfig(
        issuer = application.environment.config.property("jwt.issuer").getString(),
        audience = application.environment.config.property("jwt.audience").getString(),
        expiresIn = application.environment.config.property("jwt.refresh.expiresIn").getString().toLong(),
        secret = application.environment.config.property("jwt.refresh.secret").getString(),
    )

    val userRepository = UserRepositoryImpl()
    val tokenRepository = RefreshTokenRepositoryImpl()

    val signupUsecase = AuthSignupUsecase(
        userRepository,
        tokenRepository,
        accessConfig,
        refreshConfig,
    )

    val loginUsecase = AuthLoginUsecase(
        userRepository,
        tokenRepository,
        accessConfig,
        refreshConfig,
    )

    val controller = presentation.controller.AuthController(
        signupUsecase,
        loginUsecase,
        AuthLogoutUsecase(
            userRepository,
            tokenRepository
        )
    )

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