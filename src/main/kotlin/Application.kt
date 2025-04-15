import core.config.*
import core.security.hashing.impl.SHA256HashingService
import core.security.tokens.TokenConfig
import data.repository.RefreshTokenRepositoryImpl
import data.repository.UserRepositoryImpl
import domain.service.AuthService
import domain.service.UserService
import io.ktor.server.application.*
import io.ktor.server.routing.*
import presentation.controller.AuthController
import presentation.controller.UserController
import presentation.routes.configureAuthRoute
import presentation.routes.configureUsersRoute

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureSecurity()
    configureSerialization()
    configureDatabase()
    configureSessions()
    configureCORS()

    val accessConfig = TokenConfig(
        issuer = environment.config.property("jwt.issuer").getString(),
        audience = environment.config.property("jwt.audience").getString(),
        expiresIn = environment.config.property("jwt.access.expiresIn").getString().toLong(),
        secret = environment.config.property("jwt.access.secret").getString(),
    )
    val refreshConfig = TokenConfig(
        issuer = environment.config.property("jwt.issuer").getString(),
        audience = environment.config.property("jwt.audience").getString(),
        expiresIn = environment.config.property("jwt.refresh.expiresIn").getString().toLong(),
        secret = environment.config.property("jwt.refresh.secret").getString(),
    )

    val userRepository = UserRepositoryImpl()
    val tokenRepository = RefreshTokenRepositoryImpl()

    val hashingService = SHA256HashingService()

    val authService = AuthService(
        userRepository,
        tokenRepository,
        accessConfig,
        refreshConfig,
    )
    val userService = UserService(
        userRepository,
        hashingService,
    )

    val authController = AuthController(authService)
    val userController = UserController(userService)

    routing {
        route("api/") {
            configureAuthRoute(authController)
            configureUsersRoute(userController)
        }
    }
}
