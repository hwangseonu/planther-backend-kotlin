package me.mocha.planther.common.security.user

import me.mocha.planther.common.model.entity.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

fun create(user: User): UserPrincipal{
    val authorities = ArrayList<GrantedAuthority>()
    authorities.add(SimpleGrantedAuthority(user.role))
    return UserPrincipal(user.username, user.password, authorities);
}

class UserPrincipal(
        private val username: String,
        private val password: String,
        private val authorities: Collection<GrantedAuthority>) : UserDetails {

    override fun getUsername() = username
    override fun getPassword() = password
    override fun getAuthorities() = authorities
    override fun isAccountNonExpired() = true
    override fun isEnabled() = true
    override fun isCredentialsNonExpired() = true
    override fun isAccountNonLocked() = true
}