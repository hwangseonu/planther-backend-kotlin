package me.mocha.planther.common.security.user

import me.mocha.planther.common.model.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl : UserDetailsService {

    @Autowired
    lateinit var userRepository: UserRepository

    override fun loadUserByUsername(username: String?): UserDetails {
        val user = userRepository.findById(username!!).orElseThrow { UsernameNotFoundException("사용자를 찾을 수 없습니다.") }
        return create(user)
    }

}