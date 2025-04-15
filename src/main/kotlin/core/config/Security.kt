package core.config

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.http.auth.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.sessions.*
import domain.model.UserRole

fun Application.configureSecurity() {
    val basicVerifier = JWT
        .require(Algorithm.HMAC256(environment.config.property("jwt.access.secret").getString()))
        .withAudience(environment.config.property("jwt.audience").getString())
        .withIssuer(environment.config.property("jwt.issuer").getString())
        .acceptExpiresAt(environment.config.property("jwt.access.expiresIn").getString().toLong())
        .build()
    val refreshVerifier = JWT
        .require(Algorithm.HMAC256(environment.config.property("jwt.refresh.secret").getString()))
        .withAudience(environment.config.property("jwt.audience").getString())
        .withIssuer(environment.config.property("jwt.issuer").getString())
        .acceptExpiresAt(environment.config.property("jwt.refresh.expiresIn").getString().toLong())
        .build()

    authentication {
        jwt("basic") {
            verifier(basicVerifier)
            validate { credential ->
                val role = credential.payload.getClaim("role").asString()
                val r1 = UserRole.USER.toString()
                if (role == UserRole.USER.toString() || role == UserRole.ADMIN.toString()) {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }
        }
        jwt("admin") {
            verifier(basicVerifier)
            validate { credential ->
                val role = credential.payload.getClaim("role").asString()
                if (role == UserRole.ADMIN.toString()) {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }
        }
        jwt("refresh") {
            verifier(refreshVerifier)
            authHeader {
                val oldHeader = it.request.parseAuthorizationHeader()
                val jwt = it.sessions.get<JWTToken>()
                jwt?.token?.let { token ->
                    HttpAuthHeader.Single(oldHeader?.authScheme ?: "Bearer", token)
                } ?: oldHeader
            }

            // TODO: PROPER VALIDATION
            validate { jwtCredential ->
                JWTPrincipal(jwtCredential.payload)
            }
        }
    }
}