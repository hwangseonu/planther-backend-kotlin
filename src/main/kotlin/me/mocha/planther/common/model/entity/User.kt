package me.mocha.planther.common.model.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class User(
        @Id val username: String,
        @JsonIgnore val password: String,
        val name: String,
        val grade: Int,
        val cls: Int,
        val number: Int,
        @JsonIgnore val roles: ArrayList<String>) {

    public fun getStudentId(): String {
        return String.format("%d%02d%02d", grade, cls, number)
    }

    public fun getClassId(): String {
        return String.format("%d%02d", grade, cls)
    }
}