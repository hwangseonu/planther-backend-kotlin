package me.mocha.planther.plans.model.repository

import me.mocha.planther.plans.model.entity.Plan
import org.springframework.data.jpa.repository.JpaRepository

interface PlanRepository : JpaRepository<Plan, Long>