package presentation.routes

import core.utils.Result
import domain.model.BuildingDto
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import presentation.controller.BuildingController
import presentation.dto.request.BuildingCreateRequest
import presentation.dto.request.BuildingUpdateRequest

fun Route.configureBuildingRoute(
    controller: BuildingController,
) {
    route("/buildings") {
        post("/") {
            val building = call.receiveNullable<BuildingCreateRequest>()
                ?: return@post call.respond(HttpStatusCode.BadRequest)
            when (val result = controller.create(building)) {
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
        get("/all") {
            when (val result = controller.getAll()) {
                is Result.Failure -> call.respond(HttpStatusCode.BadRequest, mapOf("error" to result.message!!))
                is Result.Success -> call.respond(HttpStatusCode.OK, result.data!!)
            }
        }
        put("/{id}") {
            val id = call.parameters["id"]
                ?: return@put call.respond(HttpStatusCode.BadRequest)
            val request = call.receiveNullable<BuildingUpdateRequest>()
                ?: return@put call.respond(HttpStatusCode.BadRequest)
            when (val result = controller.update(id, request)) {
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
