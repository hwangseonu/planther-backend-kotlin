package me.mocha.planther.common.security.jwt

import me.mocha.planther.common.model.repository.UserRepository
import me.mocha.planther.common.security.user.UserDetailsServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
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

    @Autowired
    lateinit var userDetailsService: UserDetailsServiceImpl

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        try {
            val token = getTokenFromRequestHeader(request)
            if (StringUtils.hasText(token)) {
                if (jwtProvider.validToken(token, JwtType.ACCESS)) {
                    val username = jwtProvider.getUsernameFromToken(token)
                    if (!userRepository.existsById(username)) {
                        response.sendError(400)
                        return
                    }
                    val userDetails = userDetailsService.loadUserByUsername(username);
                    val authentication = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
                    SecurityContextHolder.getContext().authentication = authentication
                } else {
                    response.sendError(422)
                    return
                }
            }
        } catch (e: Exception) {
            response.sendError(500, e.message)
            return
        }
        filterChain.doFilter(request, response)
    }

    fun getTokenFromRequestHeader(request: HttpServletRequest): String {
        val header = request.getHeader("Authorization")
        if (StringUtils.hasText(header) && header.startsWith("Bearer")) {
            return header.replaceFirst("Bearer ", "").trim()
        }
        return ""
    }

}