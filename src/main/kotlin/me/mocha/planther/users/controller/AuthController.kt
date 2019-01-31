package me.mocha.planther.users.controller

import me.mocha.planther.common.model.repository.UserRepository
import me.mocha.planther.common.security.jwt.JwtProvider
import me.mocha.planther.common.security.jwt.JwtType
import me.mocha.planther.users.request.SignInRequest
import me.mocha.planther.users.response.SignInResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/auth")
class AuthController {

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var jwtProvider: JwtProvider

    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    @PostMapping
    fun signIn(@Valid @RequestBody request: SignInRequest): ResponseEntity<Any> {
        val user = userRepository.findById(request.username).orElse(null) ?: return ResponseEntity.notFound().build()
        if (!passwordEncoder.matches(request.password, user.password)) {
            return ResponseEntity.status(401).body(null)
        }

        return ResponseEntity.ok(SignInResponse(
                jwtProvider.generateToken(user.username, JwtType.ACCESS),
                jwtProvider.generateToken(user.username, JwtType.REFRESH)))
    }


}