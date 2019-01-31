package me.mocha.planther.plans.controller

import me.mocha.planther.common.annotation.CurrentUser
import me.mocha.planther.common.model.entity.User
import me.mocha.planther.plans.model.entity.Plan
import me.mocha.planther.plans.model.entity.PlanType
import me.mocha.planther.plans.model.repository.PlanRepository
import me.mocha.planther.plans.request.AddPlanRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/plans")
class PlanController {

    @Autowired
    lateinit var planRepository: PlanRepository

    @PostMapping
    fun addPlan(@CurrentUser user: User, @Valid @RequestBody request: AddPlanRequest): ResponseEntity<Any> {
        val plan = planRepository.save(Plan(
                request.title,
                request.content,
                PlanType.valueOf(request.type.toUpperCase()),
                request.year,
                request.month,
                request.day,
                user,
                user.getClassId()))
        return ResponseEntity.status(HttpStatus.CREATED).body(plan)
    }

    @DeleteMapping("/{id}")
    fun deletePlan(@PathVariable("id") id: Long): ResponseEntity<Any> {
        if (!planRepository.existsById(id)) {
            return ResponseEntity.notFound().build()
        }
        planRepository.deleteById(id)
        return ResponseEntity.ok().body(null)
    }

    @GetMapping("/{year}/{month}/{day}")
    fun getDayPlans(
            @CurrentUser user: User,
            @PathVariable("year") year: Int,
            @PathVariable("month") month: Int,
            @PathVariable("day") day: Int
    ) = planRepository.findAllByClassIdAndYearAndMonthAndDay(user.getClassId(), year, month, day)

}