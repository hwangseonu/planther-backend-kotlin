package me.mocha.planther.common.security.jwt

enum class JwtType(private val type: String) {
    ACCESS("access"),
    REFRESH("refresh");

    override fun toString(): String {
        return type
    }
}