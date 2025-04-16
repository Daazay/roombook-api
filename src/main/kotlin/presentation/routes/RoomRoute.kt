package presentation.routes

import core.utils.Result
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import presentation.controller.RoomController
import presentation.dto.request.RoomCreateRequest
import presentation.dto.request.RoomUpdateRequest

fun Route.configureRoomRoute(
    controller: RoomController,
) {
    route("/rooms") {
        post("/") {
            val room = call.receiveNullable<RoomCreateRequest>()
                ?: return@post call.respond(HttpStatusCode.BadRequest)
            when (val result = controller.create(room)) {
                is Result.Failure -> call.respond(HttpStatusCode.BadRequest, mapOf("error" to result.message!!))
                is Result.Success -> call.respond(HttpStatusCode.Created, result.data!!)
            }
        }
        get("/{id}") {
            val id = call.parameters["id"]
                ?: return@get call.respond(HttpStatusCode.BadRequest)
            when (val result = controller.getById(id)) {
                is Result.Failure -> call.respond(HttpStatusCode.BadRequest, mapOf("error" to result.message!!))
                is Result.Success -> call.respond(HttpStatusCode.OK, result.data!!)
            }
        }
        get("/building/{buildingId}") {
            val buildingId = call.parameters["buildingId"]
                ?: return@get call.respond(HttpStatusCode.BadRequest)
            when (val result = controller.getByBuildingId(buildingId)) {
                is Result.Failure -> call.respond(HttpStatusCode.BadRequest, mapOf("error" to result.message!!))
                is Result.Success -> call.respond(HttpStatusCode.OK, result.data!!)
            }
        }
        get("/all") {
            when (val result = controller.getAll()) {
                is Result.Failure -> call.respond(HttpStatusCode.BadRequest, mapOf("error" to result.message!!))
                is Result.Success -> call.respond(HttpStatusCode.OK, result.data!!)
            }
        }
        put("/{id}") {
            val id = call.parameters["id"]
                ?: return@put call.respond(HttpStatusCode.BadRequest)
            val room = call.receiveNullable<RoomUpdateRequest>()
                ?: return@put call.respond(HttpStatusCode.BadRequest)
            when (val result = controller.update(id, room)) {
                is Result.Failure -> call.respond(HttpStatusCode.BadRequest, mapOf("error" to result.message!!))
                is Result.Success -> call.respond(HttpStatusCode.OK, result.data!!)
            }
        }
        authenticate("admin") {
            delete("/{id}") {
                val id = call.parameters["id"]
                    ?: return@delete call.respond(HttpStatusCode.BadRequest)
                when (val result = controller.deleteById(id)) {
                    is Result.Failure -> call.respond(HttpStatusCode.BadRequest, mapOf("error" to result.message!!))
                    is Result.Success -> call.respond(HttpStatusCode.OK, result.data!!)
                }
            }
        }
    }
}
