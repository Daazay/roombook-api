package core.security.tokens.impl

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import core.security.tokens.*
import java.util.*

class JWTTokenService: TokenService {
    override fun generate(config: TokenConfig, vararg claims: TokenClaim): String {
        var token = JWT.create()
            .withAudience(config.audience)
            .withIssuer(config.issuer)
            .withExpiresAt(Date(System.currentTimeMillis() + config.expiresIn))

        claims.forEach { claim ->
            token = token.withClaim(claim.name, claim.value)
        }
        return token.sign(Algorithm.HMAC256(config.secret))
    }

    override fun generateTokens(accessConfig: TokenConfig, refreshConfig: TokenConfig, vararg claims: TokenClaim): Pair<AccessToken, RefreshToken> {
        val access = generate(accessConfig, *claims)
        val refresh = generate(refreshConfig, *claims)
        return Pair(access, refresh)
    }
}
