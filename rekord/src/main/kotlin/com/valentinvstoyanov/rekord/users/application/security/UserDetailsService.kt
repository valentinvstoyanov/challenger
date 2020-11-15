package com.valentinvstoyanov.rekord.users.application.security

import com.valentinvstoyanov.rekord.users.domain.UserService
import com.valentinvstoyanov.rekord.users.domain.model.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.UserDetails
import reactor.core.publisher.Mono

class UserDetailsService(private val userService: UserService) : ReactiveUserDetailsService {
    override fun findByUsername(id: String?): Mono<UserDetails> =
        Mono.justOrEmpty(id)
            .flatMap { userService.getUserById(it) }
            .map { createUserDetails(it) }

    private fun createUserDetails(user: User): UserDetails = object : UserDetails {
        override fun getAuthorities(): MutableCollection<out GrantedAuthority> = mutableListOf()

        override fun isEnabled(): Boolean = user.enabled

        override fun getUsername(): String = user.id

        override fun isCredentialsNonExpired(): Boolean = user.enabled

        override fun getPassword(): String = user.password

        override fun isAccountNonExpired(): Boolean = user.enabled

        override fun isAccountNonLocked(): Boolean = user.enabled
    }
}