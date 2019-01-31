package me.mocha.planther.common.security.jwt

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import io.jsonwebtoken.JwtException


class JwtProvider {
    @Value("\${jwt.access.exp}")
    var accessExp: Long? = null

    @Value("\${jwt.refresh.exp")
    var refreshExp: Long? = null

    val secret: String by lazy { System.getenv("JWT_SECRET") }

    public fun generateToken(username: String, type: JwtType): String = Jwts.builder()
            .setSubject(type.toString())
            .setExpiration(Date(System.currentTimeMillis() + (if (type == JwtType.ACCESS) accessExp!! else refreshExp!!)))
            .setIssuedAt(Date())
            .setNotBefore(Date())
            .setId(UUID.randomUUID().toString())
            .claim("username", username)
            .signWith(SignatureAlgorithm.HS512, secret)
            .compact()

    public fun getUsernameFromToken(token: String): String = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).body["username"].toString()

    public fun getTokenExp(token: String): LocalDateTime = LocalDateTime.ofInstant(Jwts.parser().setSigningKey(secret).parseClaimsJws(token).body.expiration.toInstant(), ZoneId.systemDefault())

    public fun validToken(token: String, type: JwtType): Boolean {
        return try {
            Jwts.parser().requireSubject(type.toString()).setSigningKey(secret).parseClaimsJws(token)
            true
        } catch (e: JwtException) {
            false
        }

    }
}