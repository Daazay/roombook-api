import core.config.*
import io.ktor.server.application.*
import io.ktor.server.routing.*
import presentation.routes.configureAuthRoute

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureSecurity()
    configureSerialization()
    configureDatabase()
    configureSessions()
    configureCORS()

    routing {
        route("api/") {
            configureAuthRoute()
        }
    }
}
