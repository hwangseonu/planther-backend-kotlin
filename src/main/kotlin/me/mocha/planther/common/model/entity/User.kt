package me.mocha.planther.common.model.entity

import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class User(
    @Id val username: String,
    val password: String,
    val name: String,
    val grade: Int,
    val cls: Int,
    val number: Int)