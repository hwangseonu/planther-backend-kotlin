package me.mocha.planther.user.controller

import me.mocha.planther.common.model.entity.User
import me.mocha.planther.common.model.repository.UserRepository
import me.mocha.planther.user.request.SignUpRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController {

    @Autowired
    lateinit var userRepository: UserRepository

    @PostMapping
    fun signUp(@RequestBody req: SignUpRequest): ResponseEntity<Any> {
        if (userRepository.existsById(req.username) || userRepository.existsByGradeAndClsAndNumber(req.grade, req.cls, req.number)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null)
        }
        val user = userRepository.save(User(
                req.username,
                req.password,
                req.name,
                req.grade,
                req.cls,
                req.number,
                "ROLE_USER"
        ))
        return ResponseEntity.status(HttpStatus.CREATED).body(user)
    }

}