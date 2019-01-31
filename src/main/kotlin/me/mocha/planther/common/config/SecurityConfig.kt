package me.mocha.planther.common.config

import me.mocha.planther.common.security.jwt.JwtAuthenticationEntryPoint
import me.mocha.planther.common.security.jwt.JwtAuthenticationFilter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig : WebSecurityConfigurerAdapter() {

    @Autowired
    lateinit var jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint

    @Autowired
    lateinit var jwtAuthenticationFilter: JwtAuthenticationFilter

    override fun configure(http: HttpSecurity) {
        http.csrf().disable().cors().and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/users").permitAll()
                .anyRequest().authenticated()
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
    }

}