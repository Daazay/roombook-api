package core.security.tokens

typealias AccessToken = String
typealias RefreshToken = String

interface TokenService {
    fun generate(config: TokenConfig, vararg claims: TokenClaim): String
    fun generateTokens(accessConfig: TokenConfig, refreshConfig: TokenConfig, vararg claims: TokenClaim): Pair<AccessToken, RefreshToken>
}