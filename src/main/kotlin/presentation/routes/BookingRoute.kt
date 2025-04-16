package presentation.routes

import core.utils.Result
import domain.model.BookingDto
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import presentation.controller.BookingController
import presentation.dto.request.BookingCreateRequest
import presentation.dto.request.BookingUpdateRequest

fun Route.configureBookingRoute(
    controller: BookingController,
) {
    route("/bookings") {
        post("/") {
            val booking = call.receiveNullable<BookingCreateRequest>()
                ?: return@post call.respond(HttpStatusCode.BadRequest)
            when (val result = controller.create(booking)) {
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
            val booking = call.receiveNullable<BookingUpdateRequest>()
                ?: return@put call.respond(HttpStatusCode.BadRequest)
            when (val result = controller.update(id, booking)) {
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
