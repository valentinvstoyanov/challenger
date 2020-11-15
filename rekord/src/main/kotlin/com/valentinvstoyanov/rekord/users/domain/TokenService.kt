package com.valentinvstoyanov.rekord.users.domain

import com.valentinvstoyanov.rekord.users.domain.model.*
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import reactor.core.publisher.Mono
import java.time.Instant
import java.util.*

interface TokenService {
    fun createToken(user: User): Token
    fun parseToken(token: Token): Mono<TokenContent>
}

class JwtTokenService(secret: String, private val expirationMillis: Long) : TokenService {
    private val secretKey = Keys.hmacShaKeyFor(secret.toByteArray())

    override fun createToken(user: User): Token {
        val now = Instant.now()
        val jwt = Jwts.builder()
            .setSubject(user.username)
            .setIssuer(user.id)
            .setIssuedAt(Date.from(now))
            .setExpiration(Date.from(now.plusMillis(expirationMillis)))
            .signWith(secretKey)
            .compact()

        return Token(Token.BEARER + jwt)
    }

    override fun parseToken(token: Token): Mono<TokenContent> = Mono.fromCallable {
        val jwt = token.jwt.substring(Token.BEARER.length)

        val claims = Jwts.parser()
            .setSigningKey(secretKey)
            .parseClaimsJws(jwt)
            .body

        TokenContent(
            userId = claims.issuer,
            username = claims.subject,
            expiration = claims.expiration.toInstant()
        )
    }.onErrorMap {
        if (it is ExpiredJwtException) TokenExpiredException() else InvalidTokenException()
    }
}