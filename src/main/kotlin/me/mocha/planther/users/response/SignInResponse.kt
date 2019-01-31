package me.mocha.planther.users.response

data class SignInResponse(
        val access: String,
        var refresh: String?)