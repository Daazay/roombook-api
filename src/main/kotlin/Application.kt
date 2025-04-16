import core.config.*
import core.security.hashing.impl.SHA256HashingService
import core.security.tokens.TokenConfig
import data.repository.*
import domain.service.*
import io.ktor.server.application.*
import io.ktor.server.routing.*
import presentation.controller.*
import presentation.routes.*

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

    // REPOSITORIES
    val userRepository = UserRepositoryImpl()
    val tokenRepository = RefreshTokenRepositoryImpl()

    val buildingRepository = BuildingRepositoryImpl()
    val roomRepository = RoomRepositoryImpl()
    val bookingRepository = BookingRepositoryImpl()

    // SERVICES
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

    val buildingService = BuildingService(buildingRepository)
    val roomService = RoomService(roomRepository)
    val bookingService = BookingService(bookingRepository)

    // CONTROLLERS
    val authController = AuthController(authService)
    val userController = UserController(userService)

    val buildingController = BuildingController(buildingService)
    val roomController = RoomController(roomService)
    val bookingController = BookingController(bookingService)

    routing {
        route("api/") {
            configureAuthRoute(authController)
            configureUsersRoute(userController)
            configureBuildingRoute(buildingController)
            configureRoomRoute(roomController)
            configureBookingRoute(bookingController)
        }
    }
}