package me.mocha.planther.common.resolver

import me.mocha.planther.common.annotation.CurrentUser
import me.mocha.planther.common.model.entity.User
import me.mocha.planther.common.model.repository.UserRepository
import me.mocha.planther.common.security.user.UserPrincipal
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.MethodParameter
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

@Component
class UserArgumentResolver : HandlerMethodArgumentResolver {

    @Autowired
    lateinit var userRepository: UserRepository

    override fun supportsParameter(parameter: MethodParameter) = parameter.hasParameterAnnotation(CurrentUser::class.java) && parameter.parameterType == User::class.java

    override fun resolveArgument(parameter: MethodParameter, mavContainer: ModelAndViewContainer?, webRequest: NativeWebRequest, binderFactory: WebDataBinderFactory?): Any? {
        val principal = SecurityContextHolder.getContext().authentication.principal as UserPrincipal
        return userRepository.findById(principal.username).orElseThrow{ UsernameNotFoundException("사용자를 찾을 수 없습니다.") }
    }

}