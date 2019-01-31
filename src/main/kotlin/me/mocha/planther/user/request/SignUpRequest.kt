package me.mocha.planther.user.request

import org.hibernate.validator.constraints.Range
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

data class SignUpRequest(
        @get:NotNull
        @get:NotBlank
        @get:Size(min = 4)
        val username: String,

        @get:NotNull
        @get:NotBlank
        @get:Size(min = 8)
        val password: String,

        @get:NotNull
        @get:NotBlank
        @get:Pattern(regexp = "^[가-힣]{2,5}$")
        val name: String,

        @get:NotNull
        @get:Range(min = 1, max = 3)
        val grade: Int,

        @get:NotNull
        @Range(min = 1, max = 4)
        val cls: Int,

        @get:NotNull
        @get:Range(min = 1, max = 25)
        val number: Int)