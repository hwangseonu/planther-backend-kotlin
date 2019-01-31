package me.mocha.planther.plans.request

import org.hibernate.validator.constraints.Range
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class AddPlanRequest(
        @get:NotNull
        @get:NotBlank
        @get:Size(min = 1)
        val title: String,

        @get:NotNull
        @get:NotBlank
        val content: String,

        @get:NotNull
        @get:NotBlank
        val type: String,

        @get:NotNull
        @get:Range(min = 2018, max = 2100)
        val year: Int,

        @get:NotNull
        @get:Range(min= 1, max = 12)
        val month: Int,

        @get:NotNull
        @get:Range(min = 1, max = 31)
        val day: Int)