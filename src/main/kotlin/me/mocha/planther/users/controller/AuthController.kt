package me.mocha.planther.users.controller

import me.mocha.planther.common.model.repository.UserRepository
import me.mocha.planther.common.security.jwt.JwtProvider
import me.mocha.planther.common.security.jwt.JwtType
import me.mocha.planther.users.request.SignInRequest
import me.mocha.planther.users.response.SignInResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.util.StringUtils
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.time.temporal.ChronoUnit
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

    @GetMapping
    fun refresh(@RequestHeader("Authorization") header: String?): ResponseEntity<Any> {
        if (StringUtils.hasText(header) && header != null) {
            val token = header.replaceFirst("Bearer", "").trim()
            if (StringUtils.hasText(token) && jwtProvider.validToken(token, JwtType.REFRESH)) {
                val username = jwtProvider.getUsernameFromToken(token)
                if (userRepository.existsById(username)) {
                    val response = SignInResponse(jwtProvider.generateToken(username, JwtType.ACCESS), null)
                    if (ChronoUnit.DAYS.between(LocalDate.now(), jwtProvider.getTokenExp(token)) <= 7) {
                        response.refresh = jwtProvider.generateToken(username, JwtType.REFRESH)
                    }
                    return ResponseEntity.ok(response)
                }
            }
        }
        return ResponseEntity.badRequest().body(null)
    }

}