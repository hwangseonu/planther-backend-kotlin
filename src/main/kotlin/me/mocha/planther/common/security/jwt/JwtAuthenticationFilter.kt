package me.mocha.planther.common.security.jwt

import me.mocha.planther.common.model.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtAuthenticationFilter : OncePerRequestFilter() {

    @Autowired
    lateinit var jwtProvider: JwtProvider

    @Autowired
    lateinit var userRepository: UserRepository

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        try {
            val token = getTokenFromRequestHeader(request)
            if (StringUtils.hasText(token)) {
                if (jwtProvider.validToken(token, JwtType.ACCESS)) {
                    val username = jwtProvider.getUsernameFromToken(token)
                    if (!userRepository.existsById(username)) {
                        response.sendError(400, "존재하지 않는 사용자입니다.")
                        return
                    }
                    TODO("implement more") //TODO implement more
                }
            }
        }
    }

    fun getTokenFromRequestHeader(request: HttpServletRequest): String {
        val header = request.getHeader("Authorization")
        if (StringUtils.hasText(header) && header.startsWith("Bearer")) {
            return header.replaceFirst("Bearer ", "").trim()
        }
        return ""
    }

}