package me.mocha.planther.user.request

data class SignUpRequest(
        val username: String,
        val password: String,
        val name: String,
        val grade: Int,
        val cls: Int,
        val number: Int)