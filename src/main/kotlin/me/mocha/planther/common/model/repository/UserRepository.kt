package me.mocha.planther.common.model.repository

import me.mocha.planther.common.model.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, String> {
    fun existsByGradeAndClsAndNumber(grade: Int, cls: Int, number: Int): Boolean
}