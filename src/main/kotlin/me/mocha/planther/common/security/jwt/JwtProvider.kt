package me.mocha.planther.common.security.jwt

import org.springframework.beans.factory.annotation.Value

class JwtProvider {
    @Value("\${jwt.access.exp}")
    var accessExp: Long? = null

    @Value("\${jwt.refresh.exp")
    var refreshExp: Long? = null

    val secret: String by lazy { System.getenv("JWT_SECRET") }

    public fun generateToken(username: String, type: JwtType): String {

    }


}