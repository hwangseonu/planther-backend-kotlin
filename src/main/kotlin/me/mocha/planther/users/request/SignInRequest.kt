package me.mocha.planther.users.request

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class SignInRequest(
        @get:NotNull
        @get:NotBlank
        @get:Size(min = 4)
        val username: String,

        @get:NotNull
        @get:NotBlank
        @get:Size(min = 8)
        val password: String)