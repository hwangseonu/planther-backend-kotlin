package me.mocha.planther.plans.model.repository

import me.mocha.planther.plans.model.entity.Plan
import org.springframework.data.jpa.repository.JpaRepository

interface PlanRepository : JpaRepository<Plan, Long> {
    fun findAllByClassIdAndYearAndMonthAndDay(classId: String, year: Int, month: Int, day: Int): List<Plan>
}