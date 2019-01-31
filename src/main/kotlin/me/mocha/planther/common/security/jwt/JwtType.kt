package me.mocha.planther.common.security.jwt

enum class JwtType(val type: String) {
    ACCESS("access"),
    REFRESH("refresh")
}