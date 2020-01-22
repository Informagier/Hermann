package de.htwesports.wesports.users

import de.htwesports.wesports.profile.Profile
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.authority.SimpleGrantedAuthority
import java.util.Collections.singletonList



class MyUserPrincipal(var user: User) : UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority>? {
        return singletonList(SimpleGrantedAuthority("User"))
    }

    override fun isEnabled(): Boolean {
        return this.user.enabled
    }

    override fun getUsername(): String {
        return this.user.email
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun getPassword(): String {
        return this.user.password
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }
    fun getProfile(): Profile?{
        return this.user.profile
    }
}