package me.mocha.planther.users.controller

import me.mocha.planther.common.model.entity.User
import me.mocha.planther.common.model.repository.UserRepository
import me.mocha.planther.users.request.SignUpRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/users")
class UserController {

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    @PostMapping
    fun signUp(@Valid @RequestBody req: SignUpRequest): ResponseEntity<Any> {
        if (userRepository.existsById(req.username) || userRepository.existsByGradeAndClsAndNumber(req.grade, req.cls, req.number)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null)
        }
        val user = userRepository.save(User(
                req.username,
                passwordEncoder.encode(req.password),
                req.name,
                req.grade,
                req.cls,
                req.number,
                "ROLE_USER"
        ))
        return ResponseEntity.status(HttpStatus.CREATED).body(user)
    }

}