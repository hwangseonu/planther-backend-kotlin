package me.mocha.planther.plans.model.entity

import me.mocha.planther.common.model.entity.User
import java.time.LocalDate
import javax.persistence.*

enum class PlanType(private val type: String) {
    ASSIGNMENT("assignment"),
    PRESENTATION("presentation"),
    EVENT("event");

    override fun toString() = type
}

@Entity
data class Plan(
        @Column(nullable = false)
        val title: String,

        @Column(nullable = false)
        val content: String,

        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        val type: PlanType,

        @Column(nullable = false)
        val year: Int,

        @Column(nullable = false)
        val month: Int,

        @Column(nullable = false)
        val day: Int,

        @ManyToOne
        val user: User,

        @Column(nullable = false)
        val classId: String
) {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null

    fun getDate() = LocalDate.of(year, month, day)
}