package me.mocha.planther.common.model.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class User(
        @Id
        @Column(nullable = false)
        val username: String,

        @Column(nullable = false)
        @JsonIgnore
        val password: String,

        @Column(nullable = false)
        val name: String,

        @Column(nullable = false)
        val grade: Int,

        @Column(nullable = false)
        val cls: Int,

        @Column(nullable = false)
        val number: Int,

        @Column(nullable = false)
        @JsonIgnore
        val role: String) {

    public fun getStudentId(): String {
        return String.format("%d%02d%02d", grade, cls, number)
    }

    public fun getClassId(): String {
        return String.format("%d%02d", grade, cls)
    }
}