package de.htwesports.wesports.users

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import javax.transaction.Transactional
import org.springframework.security.core.userdetails.UsernameNotFoundException

@Service("userDetailsService")
@Transactional
class UserDetailsServiceImpl : UserDetailsService {

    @Autowired
    private lateinit var userRepository: UserRepository

    override fun loadUserByUsername(email: String): UserDetails {
        val user = userRepository.findByEmail(email)
                ?: throw UsernameNotFoundException("No user found with username: $email")
        return User(user.email,
                user.password,
                listOf())
    }
}
